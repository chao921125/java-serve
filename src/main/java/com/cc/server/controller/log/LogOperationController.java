package com.cc.server.controller.log;

import com.cc.server.entity.log.LogOperation;
import com.cc.server.service.log.LogOperationService;
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
 * 操作日志 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "操作日志", description = "系统操作日志接口")
@RestController
@RequestMapping("/api-admin/log-operation")
public class LogOperationController extends BaseController<LogOperation, LogOperationService> {
    @Resource
    private LogOperationService logOperationService;

    @Override
    protected LogOperationService getService() {
        return logOperationService;
    }

    @Override
    protected LogOperation doGetById(Long id) {
        return logOperationService.selectLogOperationById(id);
    }

    @Override
    protected boolean doAdd(LogOperation entity) {
        return logOperationService.insertLogOperation(entity) > 0;
    }

    @Override
    protected boolean doDelete(Long id) {
        return logOperationService.deleteLogOperationById(id) > 0;
    }

    @Override
    protected boolean doUpdate(LogOperation entity) {
        return logOperationService.updateLogOperationById(entity) > 0;
    }

    @Override
    protected List<LogOperation> doList() {
        return logOperationService.selectAllLogOperation();
    }

    @Override
    protected PageResult<LogOperation> doPage(PageRequest pageRequest) {
        return logOperationService.pageLogOperation(pageRequest);
    }

    @Operation(summary = "记录操作日志")
    @PostMapping("/record")
    public ApiResponse<String> record(@RequestBody LogOperation log) {
        logOperationService.insertLogOperation(log);
        return ApiResponse.success("记录成功", null);
    }

    @Operation(summary = "按条件查询操作日志")
    @PostMapping("/query")
    public ApiResponse<List<LogOperation>> query(@RequestBody LogOperation log) {
        List<LogOperation> result = logOperationService.selectLogOperationByCondition(log);
        if (result == null || result.isEmpty()) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }
}
