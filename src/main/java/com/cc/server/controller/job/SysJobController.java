package com.cc.server.controller.job;

import com.cc.server.entity.job.SysJob;
import com.cc.server.service.job.SysJobService;
import com.cc.server.vo.job.JobConverter;
import com.cc.server.vo.job.SysJobVO;
import com.cc.frame.core.BaseController;
import com.cc.frame.core.ResultPageEntity;
import com.cc.frame.base.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "定时任务", description = "定时任务管理接口")
@RestController
@RequestMapping("/job")
public class SysJobController extends BaseController {
    @Autowired
    private SysJobService sysJobService;

    @Operation(summary = "获取全部定时任务列表")
    @GetMapping("/list")
    public ResultPageEntity list(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        startPage();
        List<SysJobVO> voList = JobConverter.toVOList(sysJobService.listAll());
        return getDataTable(voList);
    }

    @Operation(summary = "分页查询定时任务")
    @GetMapping("/page")
    public ResultPageEntity page(
            @Parameter(description = "页码", example = "1") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页数量", example = "10") @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "任务名") @RequestParam(required = false) String jobName,
            @Parameter(description = "任务组") @RequestParam(required = false) String jobGroup) {
        startPage();
        List<SysJob> list = sysJobService.lambdaQuery()
                .like(jobName != null && !jobName.isEmpty(), SysJob::getJobName, jobName)
                .eq(jobGroup != null && !jobGroup.isEmpty(), SysJob::getJobGroup, jobGroup)
                .list();
        List<SysJobVO> voList = JobConverter.toVOList(list);
        return getDataTable(voList);
    }

    @Operation(summary = "获取定时任务详情")
    @GetMapping("/{id}")
    public Result get(@Parameter(description = "任务ID") @PathVariable Long id) {
        SysJobVO vo = JobConverter.toVO(sysJobService.getById(id));
        return new Result(Result.SUCCESS_CODE, "查询成功", vo);
    }

    @Operation(summary = "新增定时任务")
    @PostMapping("/add")
    public Result add(@RequestBody SysJobVO jobVO) {
        boolean ok = sysJobService.add(JobConverter.toEntity(jobVO));
        return new Result(ok ? Result.SUCCESS_CODE : Result.FAIL_CODE, ok ? "新增成功" : "新增失败", null);
    }

    @Operation(summary = "修改定时任务")
    @PostMapping("/update")
    public Result update(@RequestBody SysJobVO jobVO) {
        boolean ok = sysJobService.update(JobConverter.toEntity(jobVO));
        return new Result(ok ? Result.SUCCESS_CODE : Result.FAIL_CODE, ok ? "修改成功" : "修改失败", null);
    }

    @Operation(summary = "删除定时任务")
    @PostMapping("/delete/{id}")
    public Result delete(@Parameter(description = "任务ID") @PathVariable Long id) {
        boolean ok = sysJobService.remove(id);
        return new Result(ok ? Result.SUCCESS_CODE : Result.FAIL_CODE, ok ? "删除成功" : "删除失败", null);
    }

    @Operation(summary = "暂停定时任务")
    @PostMapping("/pause/{id}")
    public Result pause(@Parameter(description = "任务ID") @PathVariable Long id) {
        boolean ok = sysJobService.pause(id);
        return new Result(ok ? Result.SUCCESS_CODE : Result.FAIL_CODE, ok ? "暂停成功" : "暂停失败", null);
    }

    @Operation(summary = "恢复定时任务")
    @PostMapping("/resume/{id}")
    public Result resume(@Parameter(description = "任务ID") @PathVariable Long id) {
        boolean ok = sysJobService.resume(id);
        return new Result(ok ? Result.SUCCESS_CODE : Result.FAIL_CODE, ok ? "恢复成功" : "恢复失败", null);
    }

    @Operation(summary = "立即执行定时任务")
    @PostMapping("/runOnce/{id}")
    public Result runOnce(@Parameter(description = "任务ID") @PathVariable Long id) {
        boolean ok = sysJobService.runOnce(id);
        return new Result(ok ? Result.SUCCESS_CODE : Result.FAIL_CODE, ok ? "执行成功" : "执行失败", null);
    }
} 