package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审批流定义
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_approval_flow")
public class SysApprovalFlow extends BaseEntity {

    /** 流程名称 */
    private String name;

    /** 流程编码 */
    private String code;

    /** 适用业务类型: PURCHASE_ORDER/SALES_ORDER/EXPENSE */
    private String targetType;

    /** 启用状态 */
    private Integer isEnabled;
}
