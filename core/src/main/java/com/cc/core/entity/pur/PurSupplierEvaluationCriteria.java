package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 供应商评估维度权重配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_supplier_evaluation_criteria")
public class PurSupplierEvaluationCriteria extends BaseEntity {

    /** 维度名称 */
    private String name;

    /** 维度编码 */
    private String code;

    /** 权重（0-1） */
    private BigDecimal weight;

    /** 启用状态 0-停用 1-启用 */
    private Integer isEnabled;

    /** 排序号 */
    private Integer sortOrder;
}
