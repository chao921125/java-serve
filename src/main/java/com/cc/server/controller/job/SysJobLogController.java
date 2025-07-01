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
import com.cc.server.vo.PageRequest;
import com.cc.server.vo.PageResult;

@Tag(name = "定时任务日志", description = "定时任务日志接口")
@RestController
@RequestMapping("/job/log")
public class SysJobLogController extends BaseController {
    @Autowired
    private SysJobLogService sysJobLogService;

    @Operation(summary = "分页查询任务日志")
    @GetMapping("/list")
    public PageResult<SysJobLogVO> list(@RequestParam(defaultValue = "1") int pageNum, 
                                       @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        List<SysJobLog> list = sysJobLogService.list();
        List<SysJobLogVO> voList = JobConverter.toVOLogList(list);
        int total = voList.size();
        int fromIndex = Math.min((pageRequest.getPageNum() - 1) * pageRequest.getPageSize(), total);
        int toIndex = Math.min(fromIndex + pageRequest.getPageSize(), total);
        List<SysJobLogVO> pageList = voList.subList(fromIndex, toIndex);
        return new PageResult<>(total, pageList);
    }

    @Operation(summary = "获取日志详情")
    @GetMapping("/{id}")
    public Result get(@Parameter(description = "日志ID") @PathVariable Long id) {
        SysJobLogVO vo = JobConverter.toVO(sysJobLogService.getById(id));
        return new Result(Result.SUCCESS_CODE, "查询成功", vo);
    }
} 