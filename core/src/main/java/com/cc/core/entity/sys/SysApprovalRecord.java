package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审批记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_approval_record")
public class SysApprovalRecord extends BaseEntity {

    /** 审批实例ID */
    private Long instanceId;

    /** 审批节点ID */
    private Long nodeId;

    /** 节点顺序 */
    private Integer nodeOrder;

    /** 节点名称 */
    private String nodeName;

    /** 审批人ID */
    private Long approverId;

    /** 审批人姓名 */
    private String approverName;

    /** 审批动作: APPROVE/REJECT/DELEGATE/RETURN */
    private String action;

    /** 审批意见 */
    private String comment;

    /** 转交人ID */
    private Long delegateToId;

    /** 操作时间 */
    private String actionTime;
}
