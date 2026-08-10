package com.cc.core.service.pur;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.pur.PurchasePaymentQueryDTO;
import com.cc.core.entity.pur.PurPurchasePayment;

/**
 * 采购付款单服务接口
 */
public interface PurPurchasePaymentService extends IService<PurPurchasePayment> {

    /**
     * 分页查询采购付款单
     */
    IPage<PurPurchasePayment> page(PurchasePaymentQueryDTO query);

    /**
     * 审核付款单
     */
    void approve(Long id);
}
