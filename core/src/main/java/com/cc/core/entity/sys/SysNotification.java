package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知消息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notification")
public class SysNotification extends BaseEntity {

    /** 通知标题 */
    private String title;

    /** 通知内容 */
    private String content;

    /** 类型: SYSTEM/APPROVAL/WARNING/BUSINESS */
    private String type;

    /** 级别: INFO/WARNING/URGENT */
    private String level;

    /** 发送人ID */
    private Long senderId;

    /** 发送人姓名 */
    private String senderName;

    /** 目标类型: ALL/USER/ROLE/DEPARTMENT */
    private String targetType;

    /** 目标值 */
    private String targetValue;

    /** 关联业务类型 */
    private String businessType;

    /** 关联业务ID */
    private Long businessId;

    /** 发送时间 */
    private String sendTime;

    /** 过期时间 */
    private String expireTime;
}
