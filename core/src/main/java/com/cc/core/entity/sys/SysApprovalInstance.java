package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审批实例
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_approval_instance")
public class SysApprovalInstance extends BaseEntity {

    /** 审批流ID */
    private Long flowId;

    /** 业务类型 */
    private String businessType;

    /** 业务单据ID */
    private Long businessId;

    /** 业务单号 */
    private String businessNo;

    /** 当前审批节点顺序 */
    private Integer currentNodeOrder;

    /** 总节点数 */
    private Integer totalNodes;

    /** 状态: 0-审批中 1-已通过 2-已驳回 3-已撤回 */
    private Integer status;

    /** 申请人ID */
    private Long applicantId;

    /** 申请人姓名 */
    private String applicantName;

    /** 申请时间 */
    private String appliedTime;

    /** 完成时间 */
    private String completedTime;
}
