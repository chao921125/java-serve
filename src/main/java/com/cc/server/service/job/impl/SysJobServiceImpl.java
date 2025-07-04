package com.cc.server.service.job.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.job.SysJob;
import com.cc.server.mapper.job.SysJobMapper;
import com.cc.server.service.job.SysJobService;
import org.springframework.stereotype.Service;
import java.util.List;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private static final String JOB_CLASS = "com.cc.server.job.QuartzJob";

    @Override
    public List<SysJob> listAll() { return list(); }
    @Override
    public SysJob getById(Long id) { return super.getById(id); }
    @Override
    public boolean add(SysJob job) {
        boolean saved = save(job);
        if (saved) {
            scheduleJob(job);
        }
        return saved;
    }
    @Override
    public boolean update(SysJob job) {
        boolean updated = updateById(job);
        if (updated) {
            updateJob(job);
        }
        return updated;
    }
    @Override
    public boolean remove(Long id) {
        SysJob job = getById(id);
        boolean removed = removeById(id);
        if (removed && job != null) {
            deleteJob(job);
        }
        return removed;
    }
    @Override
    public boolean pause(Long id) {
        SysJob job = getById(id);
        if (job == null) return false;
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = getJobKey(job);
            scheduler.pauseJob(jobKey);
            job.setStatus("1"); // 1=暂停
            updateById(job);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean resume(Long id) {
        SysJob job = getById(id);
        if (job == null) return false;
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = getJobKey(job);
            scheduler.resumeJob(jobKey);
            job.setStatus("0"); // 0=正常
            updateById(job);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean runOnce(Long id) {
        SysJob job = getById(id);
        if (job == null) return false;
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = getJobKey(job);
            scheduler.triggerJob(jobKey);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void scheduleJob(SysJob job) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>)Class.forName(JOB_CLASS))
                    .withIdentity(getJobKey(job))
                    .usingJobData("jobId", job.getId())
                    .build();
            CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(job))
                    .withSchedule(cronSchedule)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateJob(SysJob job) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = getTriggerKey(job);
            CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(cronSchedule)
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteJob(SysJob job) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = getJobKey(job);
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JobKey getJobKey(SysJob job) {
        return JobKey.jobKey(job.getJobName(), job.getJobGroup());
    }
    private TriggerKey getTriggerKey(SysJob job) {
        return TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
    }
} 