package com.cc.server.job;

import com.cc.server.entity.job.SysJob;
import com.cc.server.entity.job.SysJobLog;
import com.cc.server.service.job.SysJobLogService;
import com.cc.server.service.job.SysJobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class QuartzJob extends QuartzJobBean {
    @Autowired
    private SysJobService sysJobService;
    @Autowired
    private SysJobLogService sysJobLogService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Long jobId = context.getJobDetail().getJobDataMap().getLong("jobId");
        SysJob job = sysJobService.getById(jobId);
        SysJobLog log = new SysJobLog();
        log.setJobId(jobId);
        log.setJobName(job.getJobName());
        log.setJobGroup(job.getJobGroup());
        log.setInvokeTarget(job.getInvokeTarget());
        log.setStartTime(new Date());
        try {
            // 这里可以根据invokeTarget反射调用实际业务方法，演示只记录日志
            log.setJobMessage("任务执行成功");
            log.setStatus("0");
        } catch (Exception e) {
            log.setJobMessage("任务执行异常");
            log.setStatus("1");
            log.setExceptionInfo(e.getMessage());
        } finally {
            log.setEndTime(new Date());
            sysJobLogService.add(log);
        }
    }
} 