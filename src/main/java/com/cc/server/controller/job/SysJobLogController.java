package com.cc.server.controller.job;

import com.cc.server.entity.job.SysJobLog;
import com.cc.server.service.job.SysJobLogService;
import com.cc.server.vo.job.JobConverter;
import com.cc.server.vo.job.SysJobLogVO;
import com.cc.frame.core.BaseController;
import com.cc.frame.core.ResultPageEntity;
import com.cc.frame.base.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "定时任务日志", description = "定时任务日志接口")
@RestController
@RequestMapping("/job/log")
public class SysJobLogController extends BaseController {
    @Autowired
    private SysJobLogService sysJobLogService;

    @Operation(summary = "按任务ID查询日志")
    @GetMapping("/list/{jobId}")
    public Result listByJobId(@Parameter(description = "任务ID") @PathVariable Long jobId) {
        List<SysJobLogVO> voList = JobConverter.toVOLogList(sysJobLogService.listByJobId(jobId));
        return new Result(Result.SUCCESS_CODE, "查询成功", voList);
    }

    @Operation(summary = "分页查询日志")
    @GetMapping("/page")
    public ResultPageEntity page(
            @Parameter(description = "页码", example = "1") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页数量", example = "10") @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "任务ID") @RequestParam(required = false) Long jobId,
            @Parameter(description = "状态") @RequestParam(required = false) String status) {
        startPage();
        List<SysJobLog> list = sysJobLogService.lambdaQuery()
                .eq(jobId != null, SysJobLog::getJobId, jobId)
                .eq(status != null && !status.isEmpty(), SysJobLog::getStatus, status)
                .list();
        List<SysJobLogVO> voList = JobConverter.toVOLogList(list);
        return getDataTable(voList);
    }

    @Operation(summary = "获取日志详情")
    @GetMapping("/{id}")
    public Result get(@Parameter(description = "日志ID") @PathVariable Long id) {
        SysJobLogVO vo = JobConverter.toVO(sysJobLogService.getById(id));
        return new Result(Result.SUCCESS_CODE, "查询成功", vo);
    }
} 