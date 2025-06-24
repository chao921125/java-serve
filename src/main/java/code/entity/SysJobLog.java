package entity;

import java.io.Serializable;
import java.util.Date;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import java.io.Serializable;
    import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * sys_job_log 
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/
    @TableName("sys_job_log")
public class SysJobLog implements Serializable {

private static final long serialVersionUID = 1L;


            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        @TableField("job_id")
    private Long jobId;

        @TableField("job_name")
    private String jobName;

        @TableField("job_group")
    private String jobGroup;

        @TableField("invoke_target")
    private String invokeTarget;

        @TableField("job_message")
    private String jobMessage;

        @TableField("status")
    private String status;

        @TableField("exception_info")
    private String exceptionInfo;

        @TableField("start_time")
    private Date startTime;

        @TableField("end_time")
    private Date endTime;


        public Long getId() {
        return id;
        }

            public void setId(Long id) {
        this.id = id;
        }

        public Long getJobId() {
        return jobId;
        }

            public void setJobId(Long jobId) {
        this.jobId = jobId;
        }

        public String getJobName() {
        return jobName;
        }

            public void setJobName(String jobName) {
        this.jobName = jobName;
        }

        public String getJobGroup() {
        return jobGroup;
        }

            public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        }

        public String getInvokeTarget() {
        return invokeTarget;
        }

            public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
        }

        public String getJobMessage() {
        return jobMessage;
        }

            public void setJobMessage(String jobMessage) {
        this.jobMessage = jobMessage;
        }

        public String getStatus() {
        return status;
        }

            public void setStatus(String status) {
        this.status = status;
        }

        public String getExceptionInfo() {
        return exceptionInfo;
        }

            public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
        }

        public Date getStartTime() {
        return startTime;
        }

            public void setStartTime(Date startTime) {
        this.startTime = startTime;
        }

        public Date getEndTime() {
        return endTime;
        }

            public void setEndTime(Date endTime) {
        this.endTime = endTime;
        }



public SysJobLog(Long id,Long jobId,String jobName,String jobGroup,String invokeTarget,String jobMessage,String status,String exceptionInfo,Date startTime,Date endTime){
    this.id = id;
    this.jobId = jobId;
    this.jobName = jobName;
    this.jobGroup = jobGroup;
    this.invokeTarget = invokeTarget;
    this.jobMessage = jobMessage;
    this.status = status;
    this.exceptionInfo = exceptionInfo;
    this.startTime = startTime;
    this.endTime = endTime;
}

public SysJobLog(){
}

    @Override
    public String toString() {
    return "SysJobLog{" +
            "id = " + id +
            ", jobId = " + jobId +
            ", jobName = " + jobName +
            ", jobGroup = " + jobGroup +
            ", invokeTarget = " + invokeTarget +
            ", jobMessage = " + jobMessage +
            ", status = " + status +
            ", exceptionInfo = " + exceptionInfo +
            ", startTime = " + startTime +
            ", endTime = " + endTime +
    "}";
    }
}