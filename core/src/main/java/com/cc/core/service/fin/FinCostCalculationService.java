package com.cc.core.service.fin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.fin.FinCostCalculation;

/**
 * FinCostCalculation 服务接口
 */
public interface FinCostCalculationService extends IService<FinCostCalculation> {


    /**
     * 重新核算指定商品成本（移动加权平均）
     */
    void recalculate(Long productId);

    /**
     * 记录单笔交易的成本变动
     */
    void recordTransaction(Long productId, Long warehouseId, String batchNo,
        String transactionType, Long transactionId,
        java.math.BigDecimal quantity, java.math.BigDecimal unitCost);

}
