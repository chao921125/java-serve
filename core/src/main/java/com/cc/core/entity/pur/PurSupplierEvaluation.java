package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 供应商评估
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_supplier_evaluation")
public class PurSupplierEvaluation extends BaseEntity {

    /** 供应商ID */
    private Long supplierId;

    /** 评估日期 */
    private LocalDate evaluationDate;

    /** 评估人ID */
    private Long evaluatorId;

    /** 质量评分 */
    private BigDecimal qualityScore;

    /** 配送评分 */
    private BigDecimal deliveryScore;

    /** 价格评分 */
    private BigDecimal priceScore;

    /** 服务评分 */
    private BigDecimal serviceScore;

    /** 综合评分 */
    private BigDecimal totalScore;

    /** 评估结论 */
    private String evaluationResult;
}
