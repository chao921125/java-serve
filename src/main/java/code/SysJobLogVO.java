package entity;

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

@Schema(name = "SysJobLogVO对象", description = "")
public class SysJobLogVO implements Serializable {

private static final long serialVersionUID = 1L;


            @Schema(description = "主键")
    private Long id;

            @Schema(description = "-")
    private Long jobId;

            @Schema(description = "-")
    private String jobName;

            @Schema(description = "-")
    private String jobGroup;

            @Schema(description = "-")
    private String invokeTarget;

            @Schema(description = "-")
    private String jobMessage;

            @Schema(description = "-")
    private String status;

            @Schema(description = "-")
    private String exceptionInfo;

            @Schema(description = "-")
    private Date startTime;

            @Schema(description = "-")
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

    @Override
    public String toString() {
    return "SysJobLogVO{" +
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