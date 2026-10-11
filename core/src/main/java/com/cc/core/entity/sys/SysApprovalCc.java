// AI生成
package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 审批抄送记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_approval_cc")
public class SysApprovalCc extends BaseEntity {

    /** 审批实例ID */
    private Long instanceId;

    /** 抄送人节点ID */
    private Long nodeId;

    /** 抄送人ID */
    private Long ccUserId;

    /** 抄送人姓名 */
    private String ccUserName;

    /** 抄送时间 */
    private LocalDateTime ccTime;

    /** 是否已读 */
    private Integer isRead;
}