package com.cc.server.service.job;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.job.SysJobLog;
import java.util.List;

public interface SysJobLogService extends IService<SysJobLog> {
    List<SysJobLog> listByJobId(Long jobId);
    SysJobLog getById(Long id);
    boolean add(SysJobLog log);
} 