package com.cc.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.LogOperation;
import com.cc.core.service.LogOperationService;
import com.cc.framework.annotation.Log;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/monitor/operLog")
@RequiredArgsConstructor
public class LogOperationController {

    private final LogOperationService operLogService;
    private final com.cc.server.service.impl.LogOperationServiceImpl operLogServiceImpl;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('monitor:operLog:list')")
    public R<Page<LogOperation>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Integer status) {
        return R.ok(operLogServiceImpl.selectPage(pageNum, pageSize, title, userName, status));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('monitor:operLog:remove')")
    @Log(title = "操作日志", businessType = com.cc.core.enums.BusinessType.DELETE)
    public R<Void> remove(@PathVariable Long id) {
        return operLogService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 清空
     */
    @DeleteMapping("/clean")
    @PreAuthorize("hasAuthority('monitor:operLog:clean')")
    @Log(title = "操作日志", businessType = com.cc.core.enums.BusinessType.CLEAN)
    public R<Void> clean() {
        operLogService.cleanAll();
        return R.ok();
    }
}
