package com.cc.server.controller.log;

import com.cc.server.entity.log.LogLogin;
import com.cc.server.service.log.LogLoginService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.BaseController;
import com.cc.frame.core.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 登录日志 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:07
 */
@Tag(name = "登录日志", description = "系统登录日志接口")
@RestController
@RequestMapping("/api-admin/log-login")
public class LogLoginController extends BaseController<LogLogin, LogLoginService> {
    @Resource
    private LogLoginService logLoginService;

    @Override
    protected LogLoginService getService() {
        return logLoginService;
    }

    @Override
    protected LogLogin doGetById(Long id) {
        return logLoginService.selectLogLoginById(id);
    }

    @Override
    protected boolean doAdd(LogLogin entity) {
        return logLoginService.insertLogLogin(entity) > 0;
    }

    @Override
    protected boolean doDelete(Long id) {
        return logLoginService.deleteLogLoginById(id) > 0;
    }

    @Override
    protected boolean doUpdate(LogLogin entity) {
        return logLoginService.updateLogLoginById(entity) > 0;
    }

    @Override
    protected List<LogLogin> doList() {
        return logLoginService.selectAllLogLogin();
    }

    @Override
    protected PageResult<LogLogin> doPage(PageRequest pageRequest) {
        return logLoginService.pageLogLogin(pageRequest);
    }

    @Operation(summary = "记录登录日志")
    @PostMapping("/record")
    public ApiResponse<String> record(@RequestBody LogLogin log) {
        logLoginService.insertLogLogin(log);
        return ApiResponse.success("记录成功", null);
    }

    @Operation(summary = "按条件查询登录日志")
    @PostMapping("/query")
    public ApiResponse<List<LogLogin>> query(@RequestBody LogLogin log) {
        List<LogLogin> result = logLoginService.selectLogLoginByCondition(log);
        if (result == null || result.isEmpty()) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }
}
