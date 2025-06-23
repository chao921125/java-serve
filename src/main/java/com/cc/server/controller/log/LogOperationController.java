package com.cc.server.controller.log;

import com.cc.server.entity.log.LogOperation;
import com.cc.server.service.log.LogOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
@RequestMapping("/log-operation")
public class LogOperationController {
    @Resource
    private LogOperationService logOperationService;

    @Operation(summary = "记录操作日志")
    @PostMapping("/record")
    public String record(@RequestBody LogOperation log) {
        logOperationService.insertLogOperation(log);
        return "success";
    }

    @Operation(summary = "查询所有操作日志")
    @GetMapping("/list")
    public List<LogOperation> list() {
        return logOperationService.selectAllLogOperation();
    }

    @Operation(summary = "按条件查询操作日志")
    @PostMapping("/query")
    public List<LogOperation> query(@RequestBody LogOperation log) {
        return logOperationService.selectLogOperationByCondition(log);
    }

    @Operation(summary = "根据ID查询操作日志")
    @GetMapping("/{id}")
    public LogOperation getById(@PathVariable Long id) {
        return logOperationService.selectLogOperationById(id);
    }
}
