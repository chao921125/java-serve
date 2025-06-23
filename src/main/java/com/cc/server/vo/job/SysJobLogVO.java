package com.cc.server.vo.job;

import java.util.Date;

public class SysJobLogVO {
    private Long id;
    private Long jobId;
    private String jobName;
    private String jobGroup;
    private String invokeTarget;
    private String jobMessage;
    private String status;
    private String exceptionInfo;
    private Date startTime;
    private Date endTime;
    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }
    public String getJobGroup() { return jobGroup; }
    public void setJobGroup(String jobGroup) { this.jobGroup = jobGroup; }
    public String getInvokeTarget() { return invokeTarget; }
    public void setInvokeTarget(String invokeTarget) { this.invokeTarget = invokeTarget; }
    public String getJobMessage() { return jobMessage; }
    public void setJobMessage(String jobMessage) { this.jobMessage = jobMessage; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getExceptionInfo() { return exceptionInfo; }
    public void setExceptionInfo(String exceptionInfo) { this.exceptionInfo = exceptionInfo; }
    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
} 