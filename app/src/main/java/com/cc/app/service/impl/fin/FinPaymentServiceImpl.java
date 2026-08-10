package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.fin.PaymentQueryDTO;
import com.cc.core.entity.fin.FinPayment;
import com.cc.core.entity.fin.FinPaymentItem;
import com.cc.core.entity.fin.FinPayable;
import com.cc.core.mapper.fin.FinPaymentMapper;
import com.cc.core.service.fin.FinPaymentItemService;
import com.cc.core.service.fin.FinPaymentService;
import com.cc.core.service.fin.FinPayableService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 付款单服务实现
 */
@Service
@RequiredArgsConstructor
public class FinPaymentServiceImpl extends ServiceImpl<FinPaymentMapper, FinPayment>
        implements FinPaymentService {

    private final FinPaymentItemService paymentItemService;
    private final FinPayableService payableService;
    private final FinAccountServiceImpl accountService;

    @Override
    public IPage<FinPayment> page(PaymentQueryDTO query) {
        Page<FinPayment> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<FinPayment> wrapper = new LambdaQueryWrapper<>();
        if (query.getPaymentNo() != null && !query.getPaymentNo().isEmpty()) {
            wrapper.like(FinPayment::getPaymentNo, query.getPaymentNo());
        }
        if (query.getSupplierId() != null) {
            wrapper.eq(FinPayment::getSupplierId, query.getSupplierId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(FinPayment::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(FinPayment::getPaymentDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(FinPayment::getPaymentDate, query.getEndDate());
        }
        wrapper.orderByDesc(FinPayment::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        FinPayment payment = this.getById(id);
        if (payment == null) {
            throw new RuntimeException("付款单不存在");
        }
        if (payment.getStatus() != 1) {
            throw new RuntimeException("只有待审状态的付款单才能审核");
        }

        List<FinPaymentItem> items = getItems(id);
        for (FinPaymentItem item : items) {
            FinPayable payable = payableService.getById(item.getPayableId());
            if (payable == null) {
                throw new RuntimeException("应付账款不存在: " + item.getPayableId());
            }

            BigDecimal paidAmount = payable.getPaidAmount() != null
                    ? payable.getPaidAmount() : BigDecimal.ZERO;
            BigDecimal newPaidAmount = paidAmount.add(item.getAmount());
            BigDecimal newBalance = payable.getAmount().subtract(newPaidAmount);

            payable.setPaidAmount(newPaidAmount);
            payable.setBalance(newBalance);
            if (newBalance.compareTo(BigDecimal.ZERO) <= 0) {
                payable.setStatus(2); // 已核销
            } else {
                payable.setStatus(1); // 部分核销
            }
            payableService.updateById(payable);
        }

        // 账户支出
        accountService.accountOut(
                payment.getAccountId(), payment.getAmount(),
                "PAYMENT", payment.getId(), payment.getPaymentNo(),
                "付款单审核：" + payment.getPaymentNo()
        );

        payment.setStatus(2); // 已审
        payment.setApproverId(SecurityUtil.getUserId());
        payment.setApproveTime(LocalDateTime.now());
        this.updateById(payment);
    }

    @Override
    public List<FinPaymentItem> getItems(Long paymentId) {
        return paymentItemService.list(
                new LambdaQueryWrapper<FinPaymentItem>()
                        .eq(FinPaymentItem::getPaymentId, paymentId)
        );
    }
}
