package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.sal.SalesReceiptQueryDTO;
import com.cc.core.entity.fin.FinAccount;
import com.cc.core.entity.fin.FinAccountTransaction;
import com.cc.core.entity.fin.FinReceivable;
import com.cc.core.entity.sal.SalSalesReceipt;
import com.cc.core.mapper.fin.FinAccountMapper;
import com.cc.core.mapper.fin.FinAccountTransactionMapper;
import com.cc.core.mapper.fin.FinReceivableMapper;
import com.cc.core.mapper.sal.SalSalesReceiptMapper;
import com.cc.core.service.sal.SalSalesReceiptService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 销售收款单服务实现
 */
@Service
@RequiredArgsConstructor
public class SalSalesReceiptServiceImpl extends ServiceImpl<SalSalesReceiptMapper, SalSalesReceipt>
        implements SalSalesReceiptService {

    private final FinReceivableMapper finReceivableMapper;
    private final FinAccountMapper finAccountMapper;
    private final FinAccountTransactionMapper finAccountTransactionMapper;

    @Override
    public IPage<SalSalesReceipt> page(SalesReceiptQueryDTO query) {
        Page<SalSalesReceipt> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<SalSalesReceipt> wrapper = new LambdaQueryWrapper<>();
        if (query.getReceiptNo() != null && !query.getReceiptNo().isEmpty()) {
            wrapper.like(SalSalesReceipt::getReceiptNo, query.getReceiptNo());
        }
        if (query.getCustomerId() != null) {
            wrapper.eq(SalSalesReceipt::getCustomerId, query.getCustomerId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(SalSalesReceipt::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(SalSalesReceipt::getReceiptDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(SalSalesReceipt::getReceiptDate, query.getEndDate());
        }
        wrapper.orderByDesc(SalSalesReceipt::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        SalSalesReceipt receipt = this.getById(id);
        if (receipt == null) {
            throw new RuntimeException("销售收款单不存在");
        }
        if (receipt.getStatus() != 1) {
            throw new RuntimeException("只有待审核状态的收款单才能审核");
        }

        // 更新收款单状态
        receipt.setStatus(2); // 已审核
        receipt.setApproverId(SecurityUtil.getUserId());
        receipt.setApproveTime(LocalDateTime.now());
        this.updateById(receipt);

        BigDecimal amount = receipt.getAmount();
        Long operatorId = SecurityUtil.getUserId();

        // 核销应收账款：按该客户未核销的应收账款按时间顺序核销
        List<FinReceivable> receivables = finReceivableMapper.selectList(
                new LambdaQueryWrapper<FinReceivable>()
                        .eq(FinReceivable::getCustomerId, receipt.getCustomerId())
                        .in(FinReceivable::getStatus, 0, 1)
                        .orderByAsc(FinReceivable::getCreateTime)
        );

        BigDecimal remaining = amount;
        for (FinReceivable receivable : receivables) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            BigDecimal balance = receivable.getBalance();
            if (balance.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            BigDecimal applyAmount = remaining.min(balance);
            BigDecimal newReceived = receivable.getReceivedAmount().add(applyAmount);
            BigDecimal newBalance = receivable.getBalance().subtract(applyAmount);

            receivable.setReceivedAmount(newReceived);
            receivable.setBalance(newBalance);
            if (newBalance.compareTo(BigDecimal.ZERO) <= 0) {
                receivable.setStatus(2); // 已核销
            } else {
                receivable.setStatus(1); // 部分核销
            }
            finReceivableMapper.updateById(receivable);
            remaining = remaining.subtract(applyAmount);
        }

        // 更新账户余额
        if (receipt.getAccountId() != null) {
            FinAccount account = finAccountMapper.selectById(receipt.getAccountId());
            if (account != null) {
                BigDecimal balanceBefore = account.getBalance() != null
                        ? account.getBalance() : BigDecimal.ZERO;
                BigDecimal balanceAfter = balanceBefore.add(amount);
                account.setBalance(balanceAfter);
                finAccountMapper.updateById(account);

                // 写账户流水
                FinAccountTransaction transaction = new FinAccountTransaction();
                transaction.setAccountId(account.getId());
                transaction.setTransactionType(0); // 收入
                transaction.setAmount(amount);
                transaction.setBalanceBefore(balanceBefore);
                transaction.setBalanceAfter(balanceAfter);
                transaction.setSourceType("sales_receipt");
                transaction.setSourceId(receipt.getId());
                transaction.setSourceNo(receipt.getReceiptNo());
                transaction.setTransactionTime(LocalDateTime.now());
                transaction.setOperatorId(operatorId);
                finAccountTransactionMapper.insert(transaction);
            }
        }
    }

    /**
     * 生成收款单编号：RC + yyyyMMddHHmmss + 4位随机数
     */
    public static String generateReceiptNo() {
        return "RC" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ThreadLocalRandom.current().nextInt(1000, 9999);
    }
}
