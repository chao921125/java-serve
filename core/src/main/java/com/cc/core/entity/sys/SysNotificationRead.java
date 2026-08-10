package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知已读记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notification_read")
public class SysNotificationRead extends BaseEntity {

    /** 通知ID */
    private Long notificationId;

    /** 用户ID */
    private Long userId;

    /** 阅读时间 */
    private String readTime;
}
