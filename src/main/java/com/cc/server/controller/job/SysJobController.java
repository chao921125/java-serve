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
public class SysJobController extends BaseController<SysJobVO, SysJobService> {
    @Autowired
    private SysJobService sysJobService;

    @Override
    protected SysJobService getService() {
        return sysJobService;
    }

    @Override
    protected SysJobVO doGetById(Long id) {
        return JobConverter.toVO(sysJobService.getById(id));
    }

    @Override
    protected boolean doAdd(SysJobVO vo) {
        return sysJobService.add(JobConverter.toEntity(vo));
    }

    @Override
    protected boolean doDelete(Long id) {
        return sysJobService.remove(id);
    }

    @Override
    protected boolean doUpdate(SysJobVO vo) {
        return sysJobService.update(JobConverter.toEntity(vo));
    }

    @Override
    protected List<SysJobVO> doList() {
        return JobConverter.toVOList(sysJobService.listAll());
    }

    @Override
    protected PageResult<SysJobVO> doPage(PageRequest pageRequest) {
        List<SysJob> list = sysJobService.listAll();
        List<SysJobVO> voList = JobConverter.toVOList(list);
        int total = voList.size();
        int fromIndex = Math.min((pageRequest.getPageNum() - 1) * pageRequest.getPageSize(), total);
        int toIndex = Math.min(fromIndex + pageRequest.getPageSize(), total);
        List<SysJobVO> pageList = voList.subList(fromIndex, toIndex);
        return new PageResult<>(total, pageList);
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