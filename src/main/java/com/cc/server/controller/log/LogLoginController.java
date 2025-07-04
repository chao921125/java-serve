package com.cc.server.controller.log;

import com.cc.server.entity.log.LogLogin;
import com.cc.server.service.log.LogLoginService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:07
 */
@Tag(name = "登录日志", description = "系统登录日志接口")
@RestController
@RequestMapping("/api-admin/log-login")
public class LogLoginController {
    @Resource
    private LogLoginService logLoginService;

    @Operation(summary = "记录登录日志")
    @PostMapping("/record")
    public ApiResponse<String> record(@RequestBody LogLogin log) {
        logLoginService.insertLogLogin(log);
        return ApiResponse.success("记录成功", null);
    }

    @Operation(summary = "分页查询登录日志")
    @GetMapping("/list")
    public ApiResponse<PageResult<LogLogin>> list(@RequestParam(defaultValue = "1") int pageNum, 
                                    @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        return ApiResponse.success(logLoginService.pageLogLogin(pageRequest));
    }

    @Operation(summary = "按条件查询登录日志")
    @PostMapping("/query")
    public ApiResponse<java.util.List<LogLogin>> query(@RequestBody LogLogin log) {
        java.util.List<LogLogin> result = logLoginService.selectLogLoginByCondition(log);
        if (result == null || result.isEmpty()) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }

    @Operation(summary = "根据ID查询登录日志")
    @GetMapping("/{id}")
    public ApiResponse<LogLogin> getById(@PathVariable Long id) {
        LogLogin result = logLoginService.selectLogLoginById(id);
        if (result == null) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }
}
