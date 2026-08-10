package com.cc.app.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sys.SysNotification;
import com.cc.core.service.sys.SysNotificationService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SysNotification 控制器
 */
@RestController
@RequestMapping("/api/sys/notifications")
@RequiredArgsConstructor
public class SysNotificationController {

    private final SysNotificationService service;

    @GetMapping
    public R<Page<SysNotification>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysNotification> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SysNotification> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysNotification::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SysNotification> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SysNotification entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysNotification entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/send")
    public com.cc.framework.base.R<Void> sendNotification(@RequestParam String title,
            @RequestParam String content, @RequestParam String type,
            @RequestParam String level, @RequestParam String targetType,
            @RequestParam String targetValue) {
        service.sendNotification(title, content, type, level, targetType, targetValue);
        return com.cc.framework.base.R.ok();
    }

    @GetMapping("/unread-count")
    public com.cc.framework.base.R<Long> getUnreadCount(@RequestParam Long userId) {
        return com.cc.framework.base.R.ok(service.getUnreadCount(userId));
    }

    @PutMapping("/{id}/read")
    public com.cc.framework.base.R<Void> markAsRead(@PathVariable Long id, @RequestParam Long userId) {
        service.markAsRead(id, userId);
        return com.cc.framework.base.R.ok();
    }

    @PutMapping("/read-all")
    public com.cc.framework.base.R<Void> markAllAsRead(@RequestParam Long userId) {
        service.markAllAsRead(userId);
        return com.cc.framework.base.R.ok();
    }

}
