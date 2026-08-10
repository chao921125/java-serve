package com.cc.core.service.sal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sal.SalExchangeOrder;

/**
 * SalExchangeOrder 服务接口
 */
public interface SalExchangeOrderService extends IService<SalExchangeOrder> {


    /**
     * 审核通过
     */
    void approve(Long id, Long approverId);

    /**
     * 完成换货——退回入库 + 换出出库
     */
    void complete(Long id);

    /**
     * 计算差额
     */
    void calculateDifference(Long id);

}
