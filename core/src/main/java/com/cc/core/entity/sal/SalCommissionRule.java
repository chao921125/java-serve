package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 提成规则
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_commission_rule")
public class SalCommissionRule extends BaseEntity {

    /** 规则名称 */
    private String name;

    /** 计算方式: RATE/FIXED/TIER */
    private String calculationMethod;

    /** 计算基数: SALES_AMOUNT/GROSS_PROFIT/QUANTITY */
    private String baseOn;

    /** 提成比例% */
    private BigDecimal commissionRate;

    /** 固定金额 */
    private BigDecimal fixedAmount;

    /** 最低门槛 */
    private BigDecimal minThreshold;

    /** 提成上限 */
    private BigDecimal maxCap;

    /** 启用状态 */
    private Integer isEnabled;

    /** 生效日期 */
    private LocalDate startDate;

    /** 截止日期 */
    private LocalDate endDate;

    /** 适用商品类型: ALL/CATEGORY/PRODUCT */
    private String applyToProductType;
}
