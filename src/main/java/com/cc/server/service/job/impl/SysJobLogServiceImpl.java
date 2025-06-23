package com.cc.server.service.job.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.job.SysJobLog;
import com.cc.server.mapper.job.SysJobLogMapper;
import com.cc.server.service.job.SysJobLogService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {
    @Override
    public List<SysJobLog> listByJobId(Long jobId) {
        return lambdaQuery().eq(SysJobLog::getJobId, jobId).list();
    }
    @Override
    public SysJobLog getById(Long id) { return super.getById(id); }
    @Override
    public boolean add(SysJobLog log) { return save(log); }
} 