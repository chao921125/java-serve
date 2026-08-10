package com.cc.core.service.sal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sal.SalQuotation;

/**
 * SalQuotation 服务接口
 */
public interface SalQuotationService extends IService<SalQuotation> {


    /**
     * 发出报价
     */
    void issue(Long id);

    /**
     * 确认报价
     */
    void confirm(Long id);

    /**
     * 转销售订单
     */
    Long convertToOrder(Long id);

    /**
     * 标记失效
     */
    void expire(Long id);

}
