package com.cc.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.LogLogin;
import com.cc.core.service.LogLoginService;
import com.cc.framework.annotation.Log;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 登录日志控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/monitor/loginLog")
@RequiredArgsConstructor
public class LogLoginController {

    private final LogLoginService loginLogService;
    private final com.cc.server.service.impl.LogLoginServiceImpl loginLogServiceImpl;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('monitor:loginLog:list')")
    public R<Page<LogLogin>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Integer status) {
        return R.ok(loginLogServiceImpl.selectPage(pageNum, pageSize, userName, status));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('monitor:loginLog:remove')")
    @Log(title = "登录日志", businessType = com.cc.core.enums.BusinessType.DELETE)
    public R<Void> remove(@PathVariable Long id) {
        return loginLogService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 清空
     */
    @DeleteMapping("/clean")
    @PreAuthorize("hasAuthority('monitor:loginLog:clean')")
    @Log(title = "登录日志", businessType = com.cc.core.enums.BusinessType.CLEAN)
    public R<Void> clean() {
        loginLogService.cleanAll();
        return R.ok();
    }
}
