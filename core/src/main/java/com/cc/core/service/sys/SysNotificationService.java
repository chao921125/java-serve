package com.cc.core.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sys.SysNotification;

/**
 * SysNotification 服务接口
 */
public interface SysNotificationService extends IService<SysNotification> {


    /**
     * 发送系统通知
     */
    void sendNotification(String title, String content, String type, String level,
        String targetType, String targetValue);

    /**
     * 获取用户未读数量
     */
    Long getUnreadCount(Long userId);

    /**
     * 标记已读
     */
    void markAsRead(Long notificationId, Long userId);

    /**
     * 全部标记已读
     */
    void markAllAsRead(Long userId);

}
