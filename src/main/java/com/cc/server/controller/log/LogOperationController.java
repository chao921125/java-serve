package com.cc.server.controller.log;

import com.cc.server.entity.log.LogOperation;
import com.cc.server.service.log.LogOperationService;
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
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "操作日志", description = "系统操作日志接口")
@RestController
@RequestMapping("/api-admin/log-operation")
public class LogOperationController {
    @Resource
    private LogOperationService logOperationService;

    @Operation(summary = "记录操作日志")
    @PostMapping("/record")
    public ApiResponse<String> record(@RequestBody LogOperation log) {
        logOperationService.insertLogOperation(log);
        return ApiResponse.success("记录成功", null);
    }

    @Operation(summary = "分页查询操作日志")
    @GetMapping("/list")
    public ApiResponse<PageResult<LogOperation>> list(@RequestParam(defaultValue = "1") int pageNum, 
                                        @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        return ApiResponse.success(logOperationService.pageLogOperation(pageRequest));
    }

    @Operation(summary = "按条件查询操作日志")
    @PostMapping("/query")
    public ApiResponse<java.util.List<LogOperation>> query(@RequestBody LogOperation log) {
        java.util.List<LogOperation> result = logOperationService.selectLogOperationByCondition(log);
        if (result == null || result.isEmpty()) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }

    @Operation(summary = "根据ID查询操作日志")
    @GetMapping("/{id}")
    public ApiResponse<LogOperation> getById(@PathVariable Long id) {
        LogOperation result = logOperationService.selectLogOperationById(id);
        if (result == null) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }
}
