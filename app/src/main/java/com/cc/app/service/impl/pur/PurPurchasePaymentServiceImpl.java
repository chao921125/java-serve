// AI生成
package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.pur.PurchasePaymentQueryDTO;
import com.cc.core.entity.fin.FinAccount;
import com.cc.core.entity.fin.FinAccountTransaction;
import com.cc.core.entity.fin.FinPayable;
import com.cc.core.entity.pur.PurPurchasePayment;
import com.cc.core.mapper.pur.PurPurchasePaymentMapper;
import com.cc.core.service.fin.FinAccountService;
import com.cc.core.service.fin.FinAccountTransactionService;
import com.cc.core.service.fin.FinPayableService;
import com.cc.core.service.pur.PurPurchasePaymentService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购付款单服务实现
 */
@Service
@RequiredArgsConstructor
public class PurPurchasePaymentServiceImpl extends ServiceImpl<PurPurchasePaymentMapper, PurPurchasePayment>
        implements PurPurchasePaymentService {

    private final FinPayableService payableService;
    private final FinAccountService accountService;
    private final FinAccountTransactionService accountTransactionService;

    @Override
    public IPage<PurPurchasePayment> page(PurchasePaymentQueryDTO query) {
        Page<PurPurchasePayment> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<PurPurchasePayment> wrapper = new LambdaQueryWrapper<>();
        if (query.getPaymentNo() != null && !query.getPaymentNo().isEmpty()) {
            wrapper.like(PurPurchasePayment::getPaymentNo, query.getPaymentNo());
        }
        if (query.getSupplierId() != null) {
            wrapper.eq(PurPurchasePayment::getSupplierId, query.getSupplierId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(PurPurchasePayment::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(PurPurchasePayment::getPaymentDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(PurPurchasePayment::getPaymentDate, query.getEndDate());
        }
        wrapper.orderByDesc(PurPurchasePayment::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        PurPurchasePayment payment = this.getById(id);
        if (payment == null) {
            throw new RuntimeException("采购付款单不存在");
        }
        if (payment.getStatus() != 1) {
            throw new RuntimeException("只有待审核状态的付款单才能审核");
        }

        payment.setStatus(2); // 已审核
        payment.setApproverId(SecurityUtil.getUserId());
        payment.setApproveTime(LocalDateTime.now());
        this.updateById(payment);

        // 核销应付账款：按供应商的未核销应付单按到期日先后顺序核销
        BigDecimal remaining = payment.getAmount() != null ? payment.getAmount() : BigDecimal.ZERO;
        if (remaining.compareTo(BigDecimal.ZERO) > 0 && payment.getSupplierId() != null) {
            List<FinPayable> payables = payableService.lambdaQuery()
                    .eq(FinPayable::getSupplierId, payment.getSupplierId())
                    .lt(FinPayable::getStatus, 2)
                    .orderByAsc(FinPayable::getDueDate).list();
            for (FinPayable payable : payables) {
                if (remaining.compareTo(BigDecimal.ZERO) <= 0) break;
                BigDecimal balance = payable.getBalance() != null ? payable.getBalance() : BigDecimal.ZERO;
                if (balance.compareTo(BigDecimal.ZERO) <= 0) continue;

                BigDecimal writeOff = remaining.min(balance);
                payable.setPaidAmount((payable.getPaidAmount() != null ? payable.getPaidAmount() : BigDecimal.ZERO).add(writeOff));
                payable.setBalance(balance.subtract(writeOff));
                payable.setStatus(payable.getBalance().compareTo(BigDecimal.ZERO) == 0 ? 2 : 1);
                payableService.updateById(payable);
                remaining = remaining.subtract(writeOff);
            }
        }

        // 记录资金账户流水
        if (payment.getAccountId() != null) {
            FinAccount account = accountService.getById(payment.getAccountId());
            BigDecimal balanceBefore = account != null && account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;
            BigDecimal balanceAfter = balanceBefore.subtract(payment.getAmount() != null ? payment.getAmount() : BigDecimal.ZERO);

            FinAccountTransaction transaction = new FinAccountTransaction();
            transaction.setAccountId(payment.getAccountId());
            transaction.setTransactionType(1); // 支出
            transaction.setAmount(payment.getAmount());
            transaction.setBalanceBefore(balanceBefore);
            transaction.setBalanceAfter(balanceAfter);
            transaction.setSourceType("PURCHASE_PAYMENT");
            transaction.setSourceId(payment.getId());
            transaction.setSourceNo(payment.getPaymentNo());
            transaction.setTransactionTime(LocalDateTime.now());
            transaction.setOperatorId(SecurityUtil.getUserId());
            transaction.setRemark("采购付款审核核销");
            accountTransactionService.save(transaction);

            // 更新账户余额
            if (account != null) {
                account.setBalance(balanceAfter);
                accountService.updateById(account);
            }
        }
    }
}
