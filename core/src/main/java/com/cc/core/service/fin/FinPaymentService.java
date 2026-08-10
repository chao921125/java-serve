package com.cc.core.service.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.fin.PaymentQueryDTO;
import com.cc.core.entity.fin.FinPayment;
import com.cc.core.entity.fin.FinPaymentItem;

import java.util.List;

/**
 * 付款单服务接口
 */
public interface FinPaymentService extends IService<FinPayment> {

    /**
     * 分页查询付款单
     */
    IPage<FinPayment> page(PaymentQueryDTO query);

    /**
     * 审核付款单 — 核销应付账款
     */
    void approve(Long id);

    /**
     * 获取付款单明细
     */
    List<FinPaymentItem> getItems(Long paymentId);
}
