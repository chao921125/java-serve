package com.cc.server.vo.job;

import com.cc.server.entity.job.SysJob;
import com.cc.server.entity.job.SysJobLog;
import java.util.ArrayList;
import java.util.List;

public class JobConverter {
    public static SysJobVO toVO(SysJob job) {
        if (job == null) return null;
        SysJobVO vo = new SysJobVO();
        vo.setId(job.getId());
        vo.setJobName(job.getJobName());
        vo.setJobGroup(job.getJobGroup());
        vo.setInvokeTarget(job.getInvokeTarget());
        vo.setCronExpression(job.getCronExpression());
        vo.setMisfirePolicy(job.getMisfirePolicy());
        vo.setConcurrent(job.getConcurrent());
        vo.setStatus(job.getStatus());
        vo.setRemark(job.getRemark());
        vo.setCreateTime(job.getCreateTime());
        vo.setUpdateTime(job.getUpdateTime());
        return vo;
    }
    public static List<SysJobVO> toVOList(List<SysJob> jobs) {
        List<SysJobVO> list = new ArrayList<>();
        if (jobs != null) for (SysJob job : jobs) list.add(toVO(job));
        return list;
    }
    public static SysJob toEntity(SysJobVO vo) {
        if (vo == null) return null;
        SysJob job = new SysJob();
        job.setId(vo.getId());
        job.setJobName(vo.getJobName());
        job.setJobGroup(vo.getJobGroup());
        job.setInvokeTarget(vo.getInvokeTarget());
        job.setCronExpression(vo.getCronExpression());
        job.setMisfirePolicy(vo.getMisfirePolicy());
        job.setConcurrent(vo.getConcurrent());
        job.setStatus(vo.getStatus());
        job.setRemark(vo.getRemark());
        job.setCreateTime(vo.getCreateTime());
        job.setUpdateTime(vo.getUpdateTime());
        return job;
    }
    public static List<SysJob> toEntityList(List<SysJobVO> vos) {
        List<SysJob> list = new ArrayList<>();
        if (vos != null) for (SysJobVO vo : vos) list.add(toEntity(vo));
        return list;
    }
    public static SysJobLogVO toVO(SysJobLog log) {
        if (log == null) return null;
        SysJobLogVO vo = new SysJobLogVO();
        vo.setId(log.getId());
        vo.setJobId(log.getJobId());
        vo.setJobName(log.getJobName());
        vo.setJobGroup(log.getJobGroup());
        vo.setInvokeTarget(log.getInvokeTarget());
        vo.setJobMessage(log.getJobMessage());
        vo.setStatus(log.getStatus());
        vo.setExceptionInfo(log.getExceptionInfo());
        vo.setStartTime(log.getStartTime());
        vo.setEndTime(log.getEndTime());
        return vo;
    }
    public static List<SysJobLogVO> toVOLogList(List<SysJobLog> logs) {
        List<SysJobLogVO> list = new ArrayList<>();
        if (logs != null) for (SysJobLog log : logs) list.add(toVO(log));
        return list;
    }
    public static SysJobLog toEntity(SysJobLogVO vo) {
        if (vo == null) return null;
        SysJobLog log = new SysJobLog();
        log.setId(vo.getId());
        log.setJobId(vo.getJobId());
        log.setJobName(vo.getJobName());
        log.setJobGroup(vo.getJobGroup());
        log.setInvokeTarget(vo.getInvokeTarget());
        log.setJobMessage(vo.getJobMessage());
        log.setStatus(vo.getStatus());
        log.setExceptionInfo(vo.getExceptionInfo());
        log.setStartTime(vo.getStartTime());
        log.setEndTime(vo.getEndTime());
        return log;
    }
    public static List<SysJobLog> toEntityLogList(List<SysJobLogVO> vos) {
        List<SysJobLog> list = new ArrayList<>();
        if (vos != null) for (SysJobLogVO vo : vos) list.add(toEntity(vo));
        return list;
    }
} 