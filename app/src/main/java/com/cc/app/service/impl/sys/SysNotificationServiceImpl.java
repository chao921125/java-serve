package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysNotification;
import com.cc.core.mapper.sys.SysNotificationMapper;
import com.cc.core.service.sys.SysNotificationService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.cc.core.service.sys.SysNotificationReadService;
import com.cc.core.entity.sys.SysNotificationRead;
import java.util.Set;

/**
 * SysNotification 服务实现
 */
@Service
@RequiredArgsConstructor
public class SysNotificationServiceImpl extends ServiceImpl<SysNotificationMapper, SysNotification> implements SysNotificationService {
    private final SysNotificationReadService notificationReadService;


    // ==== Business Logic Methods ====

    @Override
    public void sendNotification(String title, String content, String type, String level,
            String targetType, String targetValue) {
        SysNotification notification = new SysNotification();
        notification.setTitle(title);
        notification.setContent(content);
        notification.setType(type);
        notification.setLevel(level);
        notification.setTargetType(targetType);
        notification.setTargetValue(targetValue);
        notification.setSendTime(java.time.LocalDateTime.now().toString());
        save(notification);
    }

    @Override
    public Long getUnreadCount(Long userId) {
        java.util.List<SysNotification> all = list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysNotification>()
                .eq(SysNotification::getTargetType, "ALL")
                .or()
                .eq(SysNotification::getTargetType, "USER")
                .eq(SysNotification::getTargetValue, String.valueOf(userId))
        );
        java.util.List<SysNotificationRead> reads = notificationReadService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysNotificationRead>()
                .eq(SysNotificationRead::getUserId, userId)
        );
        java.util.Set<Long> readIds = reads.stream()
            .map(SysNotificationRead::getNotificationId)
            .collect(java.util.stream.Collectors.toSet());
        return all.stream().filter(n -> !readIds.contains(n.getId())).count();
    }

    @Override
    public void markAsRead(Long notificationId, Long userId) {
        SysNotificationRead nr = new SysNotificationRead();
        nr.setNotificationId(notificationId);
        nr.setUserId(userId);
        nr.setReadTime(java.time.LocalDateTime.now().toString());
        notificationReadService.save(nr);
    }

    @Override
    public void markAllAsRead(Long userId) {
        java.util.List<SysNotification> all = list();
        for (SysNotification n : all) {
            SysNotificationRead existing = notificationReadService.getOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysNotificationRead>()
                    .eq(SysNotificationRead::getNotificationId, n.getId())
                    .eq(SysNotificationRead::getUserId, userId)
            );
            if (existing == null) {
                markAsRead(n.getId(), userId);
            }
        }
    }

}
