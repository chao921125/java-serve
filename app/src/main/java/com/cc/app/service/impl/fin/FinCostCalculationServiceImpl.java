package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.fin.FinCostCalculation;
import com.cc.core.mapper.fin.FinCostCalculationMapper;
import com.cc.core.service.fin.FinCostCalculationService;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * FinCostCalculation 服务实现
 */
@Service
public class FinCostCalculationServiceImpl extends ServiceImpl<FinCostCalculationMapper, FinCostCalculation> implements FinCostCalculationService {

    // ==== Business Logic Methods ====

    @Override
    public void recalculate(Long productId) {
        // 查询该商品所有交易记录，按时间顺序重新计算
        java.util.List<FinCostCalculation> records = list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<FinCostCalculation>()
                .eq(FinCostCalculation::getProductId, productId)
                .orderByAsc(FinCostCalculation::getCalculatedTime)
        );
        java.math.BigDecimal runningQty = java.math.BigDecimal.ZERO;
        java.math.BigDecimal runningCost = java.math.BigDecimal.ZERO;
        for (FinCostCalculation record : records) {
            if ("PURCHASE_IN".equals(record.getTransactionType()) || "RETURN_IN".equals(record.getTransactionType())) {
                // 入库：移动加权平均
                runningQty = runningQty.add(record.getTransactionQuantity());
                runningCost = runningCost.add(record.getTransactionTotalCost());
                java.math.BigDecimal avgCost = runningQty.compareTo(java.math.BigDecimal.ZERO) > 0
                    ? runningCost.divide(runningQty, 4, java.math.RoundingMode.HALF_UP)
                    : java.math.BigDecimal.ZERO;
                record.setCostAfter(avgCost);
                record.setTotalCostAfter(runningCost);
                record.setQuantityAfter(runningQty);
            } else {
                // 出库：按当前平均成本出
                java.math.BigDecimal avgCost = runningQty.compareTo(java.math.BigDecimal.ZERO) > 0
                    ? runningCost.divide(runningQty, 4, java.math.RoundingMode.HALF_UP)
                    : record.getCostBefore();
                record.setTransactionUnitCost(avgCost);
                record.setTransactionTotalCost(avgCost.multiply(record.getTransactionQuantity()));
                runningQty = runningQty.subtract(record.getTransactionQuantity());
                runningCost = runningCost.subtract(record.getTransactionTotalCost());
                record.setCostAfter(avgCost);
                record.setTotalCostAfter(runningCost);
                record.setQuantityAfter(runningQty);
            }
            record.setQuantityBefore(runningQty);
            record.setCostBefore(runningQty.compareTo(java.math.BigDecimal.ZERO) > 0
                ? runningCost.divide(runningQty, 4, java.math.RoundingMode.HALF_UP)
                : java.math.BigDecimal.ZERO);
            record.setTotalCostBefore(runningCost);
            updateById(record);
        }
    }

    @Override
    public void recordTransaction(Long productId, Long warehouseId, String batchNo,
            String transactionType, Long transactionId,
            java.math.BigDecimal quantity, java.math.BigDecimal unitCost) {
        FinCostCalculation calc = new FinCostCalculation();
        calc.setProductId(productId);
        calc.setWarehouseId(warehouseId);
        calc.setBatchNo(batchNo);
        calc.setTransactionType(transactionType);
        calc.setTransactionId(transactionId);
        calc.setTransactionQuantity(quantity);
        calc.setTransactionUnitCost(unitCost);
        calc.setTransactionTotalCost(quantity.multiply(unitCost));
        calc.setCalculatedTime(java.time.LocalDateTime.now().toString());
        save(calc);
        recalculate(productId);
    }

}
