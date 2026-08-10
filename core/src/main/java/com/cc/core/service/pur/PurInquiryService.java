package com.cc.core.service.pur;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.pur.PurInquiry;

/**
 * PurInquiry 服务接口
 */
public interface PurInquiryService extends IService<PurInquiry> {


    /**
     * 发出询价
     */
    void issue(Long id);

    /**
     * 比价——完成所有供应商报价后选择最优
     */
    void compareAndSelect(Long id, Long selectedSupplierId);

    /**
     * 转为采购订单
     */
    Long convertToPurchaseOrder(Long id);

    /**
     * 截止询价
     */
    void close(Long id);

    /**
     * 录入报价
     */
    void enterQuote(Long inquiryId, Long supplierId, Long inquiryItemId, java.math.BigDecimal unitPrice);

    /**
     * 定价（选中供应商）
     */
    void decide(Long inquiryId, Long selectedSupplierId);

}
