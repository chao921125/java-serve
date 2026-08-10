package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 成本计算记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fin_cost_calculation")
public class FinCostCalculation extends BaseEntity {

    /** 商品ID */
    private Long productId;

    /** 仓库ID */
    private Long warehouseId;

    /** 批次号 */
    private String batchNo;

    /** 交易类型: PURCHASE_IN/SALES_OUT/RETURN_IN/OTHER_IN/OTHER_OUT */
    private String transactionType;

    /** 交易单据ID */
    private Long transactionId;

    /** 交易前数量 */
    private BigDecimal quantityBefore;

    /** 交易前单位成本 */
    private BigDecimal costBefore;

    /** 交易前总成本 */
    private BigDecimal totalCostBefore;

    /** 交易数量 */
    private BigDecimal transactionQuantity;

    /** 交易单位成本 */
    private BigDecimal transactionUnitCost;

    /** 交易总成本 */
    private BigDecimal transactionTotalCost;

    /** 交易后数量 */
    private BigDecimal quantityAfter;

    /** 交易后单位成本 */
    private BigDecimal costAfter;

    /** 交易后总成本 */
    private BigDecimal totalCostAfter;

    /** 计算时间 */
    private String calculatedTime;
}
