package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审批节点
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_approval_node")
public class SysApprovalNode extends BaseEntity {

    /** 流程ID */
    private Long flowId;

    /** 节点名称 */
    private String nodeName;

    /** 节点顺序 */
    private Integer nodeOrder;

    /** 审批人类型: USER/ROLE/DEPARTMENT_LEADER/CUSTOM */
    private String approverType;

    /** 审批人值 */
    private String approverValue;

    /** 可否驳回 */
    private Integer canReject;

    /** 可否转交 */
    private Integer canDelegate;

    /** 超时时间(小时) */
    private Integer timeoutHours;

    /** 条件表达式 */
    private String conditionExpression;
}
