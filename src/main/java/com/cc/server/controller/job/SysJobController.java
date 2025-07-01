package com.cc.server.controller.job;

import com.cc.server.entity.job.SysJob;
import com.cc.server.service.job.SysJobService;
import com.cc.server.vo.job.JobConverter;
import com.cc.server.vo.job.SysJobVO;
import com.cc.frame.core.BaseController;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "定时任务", description = "定时任务管理接口")
@RestController
@RequestMapping("/api-admin/job")
public class SysJobController extends BaseController {
    @Autowired
    private SysJobService sysJobService;

    @Operation(summary = "分页查询定时任务")
    @GetMapping("/list")
    public ApiResponse<PageResult<SysJobVO>> list(@RequestParam(defaultValue = "1") int pageNum, 
                                    @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        List<SysJob> list = sysJobService.listAll();
        List<SysJobVO> voList = JobConverter.toVOList(list);
        int total = voList.size();
        int fromIndex = Math.min((pageRequest.getPageNum() - 1) * pageRequest.getPageSize(), total);
        int toIndex = Math.min(fromIndex + pageRequest.getPageSize(), total);
        List<SysJobVO> pageList = voList.subList(fromIndex, toIndex);
        return ApiResponse.success(new PageResult<>(total, pageList));
    }

    @Operation(summary = "获取定时任务详情")
    @GetMapping("/{id}")
    public ApiResponse<SysJobVO> get(@Parameter(description = "任务ID") @PathVariable Long id) {
        SysJobVO vo = JobConverter.toVO(sysJobService.getById(id));
        if (vo == null) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(vo);
    }

    @Operation(summary = "新增定时任务")
    @PostMapping("/add")
    public ApiResponse<String> add(@RequestBody SysJobVO jobVO) {
        boolean ok = sysJobService.add(JobConverter.toEntity(jobVO));
        return ok ? ApiResponse.success("新增成功", null) : ApiResponse.error(500, "新增失败");
    }

    @Operation(summary = "修改定时任务")
    @PostMapping("/update")
    public ApiResponse<String> update(@RequestBody SysJobVO jobVO) {
        boolean ok = sysJobService.update(JobConverter.toEntity(jobVO));
        return ok ? ApiResponse.success("修改成功", null) : ApiResponse.error(500, "修改失败");
    }

    @Operation(summary = "删除定时任务")
    @PostMapping("/delete/{id}")
    public ApiResponse<String> delete(@Parameter(description = "任务ID") @PathVariable Long id) {
        boolean ok = sysJobService.remove(id);
        return ok ? ApiResponse.success("删除成功", null) : ApiResponse.error(500, "删除失败");
    }

    @Operation(summary = "暂停定时任务")
    @PostMapping("/pause/{id}")
    public ApiResponse<String> pause(@Parameter(description = "任务ID") @PathVariable Long id) {
        boolean ok = sysJobService.pause(id);
        return ok ? ApiResponse.success("暂停成功", null) : ApiResponse.error(500, "暂停失败");
    }

    @Operation(summary = "恢复定时任务")
    @PostMapping("/resume/{id}")
    public ApiResponse<String> resume(@Parameter(description = "任务ID") @PathVariable Long id) {
        boolean ok = sysJobService.resume(id);
        return ok ? ApiResponse.success("恢复成功", null) : ApiResponse.error(500, "恢复失败");
    }

    @Operation(summary = "立即执行定时任务")
    @PostMapping("/runOnce/{id}")
    public ApiResponse<String> runOnce(@Parameter(description = "任务ID") @PathVariable Long id) {
        boolean ok = sysJobService.runOnce(id);
        return ok ? ApiResponse.success("执行成功", null) : ApiResponse.error(500, "执行失败");
    }
} 