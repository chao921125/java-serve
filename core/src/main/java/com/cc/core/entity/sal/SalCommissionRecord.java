package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 提成记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_commission_record")
public class SalCommissionRecord extends BaseEntity {

    /** 业务员ID */
    private Long userId;

    /** 提成规则ID */
    private Long ruleId;

    /** 提成周期 YYYY-MM */
    private String period;

    /** 来源单据类型 */
    private String sourceType;

    /** 来源单据ID */
    private Long sourceId;

    /** 计算基数 */
    private BigDecimal baseAmount;

    /** 提成金额 */
    private BigDecimal commissionAmount;

    /** 状态: 0-待发放 1-已发放 2-已冲销 */
    private Integer status;

    /** 计算时间 */
    private String calculatedTime;

    /** 发放时间 */
    private String paidTime;
}
