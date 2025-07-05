package com.cc.server.controller.job;

import com.cc.server.entity.job.SysJobLog;
import com.cc.server.service.job.SysJobLogService;
import com.cc.server.vo.job.JobConverter;
import com.cc.server.vo.job.SysJobLogVO;
import com.cc.frame.core.BaseController;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "定时任务日志", description = "定时任务日志接口")
@RestController
@RequestMapping("/api-admin/job/log")
public class SysJobLogController extends BaseController<SysJobLogVO, SysJobLogService> {
    @Resource
    private SysJobLogService sysJobLogService;

    @Override
    protected SysJobLogService getService() {
        return sysJobLogService;
    }

    @Override
    protected SysJobLogVO doGetById(Long id) {
        return JobConverter.toVO(sysJobLogService.getById(id));
    }

    @Override
    protected List<SysJobLogVO> doList() {
        return JobConverter.toVOLogList(sysJobLogService.list());
    }

    @Override
    protected PageResult<SysJobLogVO> doPage(PageRequest pageRequest) {
        List<SysJobLogVO> voList = JobConverter.toVOLogList(sysJobLogService.list());
        int total = voList.size();
        int fromIndex = Math.min((pageRequest.getPageNum() - 1) * pageRequest.getPageSize(), total);
        int toIndex = Math.min(fromIndex + pageRequest.getPageSize(), total);
        List<SysJobLogVO> pageList = voList.subList(fromIndex, toIndex);
        return new PageResult<>(total, pageList);
    }

    @Override
    protected boolean doAdd(SysJobLogVO vo) {
        return false;
    }

    @Override
    protected boolean doUpdate(SysJobLogVO vo) {
        return false;
    }

    @Override
    protected boolean doDelete(Long id) {
        return false;
    }
} 