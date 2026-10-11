// AI生成
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

    /** 节点类型: SEQUENTIAL(顺序) PARALLEL_AND(会签) PARALLEL_OR(或签) CONDITIONAL(条件) CC(抄送) */
    private String nodeType;

    /** 会签/或签所需审批人数 */
    private Integer requiredCount;

    /** 条件分支：条件为真时跳转的节点顺序 */
    private Integer trueNextOrder;

    /** 条件分支：条件为假时跳转的节点顺序 */
    private Integer falseNextOrder;
}
