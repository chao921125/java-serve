package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 提成规则适用对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_commission_rule_target")
public class SalCommissionRuleTarget extends BaseEntity {

    /** 规则ID */
    private Long ruleId;

    /** 对象类型: USER/ROLE/DEPARTMENT */
    private String targetType;

    /** 对象ID */
    private Long targetId;
}
