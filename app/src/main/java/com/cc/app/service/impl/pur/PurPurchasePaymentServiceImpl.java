package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.pur.PurchasePaymentQueryDTO;
import com.cc.core.entity.pur.PurPurchasePayment;
import com.cc.core.mapper.pur.PurPurchasePaymentMapper;
import com.cc.core.service.pur.PurPurchasePaymentService;
import com.cc.framework.config.security.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 采购付款单服务实现
 */
@Service
public class PurPurchasePaymentServiceImpl extends ServiceImpl<PurPurchasePaymentMapper, PurPurchasePayment>
        implements PurPurchasePaymentService {

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

        // TODO: 审核通过后核销应付账款（fin_payables），减少余额
        // TODO: 记录资金账户流水（fin_account_transactions）
    }
}
