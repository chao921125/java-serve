package entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * sys_job 
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/

@Schema(name = "SysJobVO对象", description = "")
public class SysJobVO implements Serializable {

private static final long serialVersionUID = 1L;


            @Schema(description = "主键")
    private Long id;

            @Schema(description = "-")
    private String jobName;

            @Schema(description = "-")
    private String jobGroup;

            @Schema(description = "-")
    private String invokeTarget;

            @Schema(description = "-")
    private String cronExpression;

            @Schema(description = "-")
    private String misfirePolicy;

            @Schema(description = "-")
    private String concurrent;

            @Schema(description = "-")
    private String status;

            @Schema(description = "-")
    private String remark;

            @Schema(description = "-")
    private Date createTime;

            @Schema(description = "-")
    private Date updateTime;


        public Long getId() {
        return id;
        }

            public void setId(Long id) {
        this.id = id;
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

        public String getCronExpression() {
        return cronExpression;
        }

            public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        }

        public String getMisfirePolicy() {
        return misfirePolicy;
        }

            public void setMisfirePolicy(String misfirePolicy) {
        this.misfirePolicy = misfirePolicy;
        }

        public String getConcurrent() {
        return concurrent;
        }

            public void setConcurrent(String concurrent) {
        this.concurrent = concurrent;
        }

        public String getStatus() {
        return status;
        }

            public void setStatus(String status) {
        this.status = status;
        }

        public String getRemark() {
        return remark;
        }

            public void setRemark(String remark) {
        this.remark = remark;
        }

        public Date getCreateTime() {
        return createTime;
        }

            public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        }

        public Date getUpdateTime() {
        return updateTime;
        }

            public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        }

    @Override
    public String toString() {
    return "SysJobVO{" +
            "id = " + id +
            ", jobName = " + jobName +
            ", jobGroup = " + jobGroup +
            ", invokeTarget = " + invokeTarget +
            ", cronExpression = " + cronExpression +
            ", misfirePolicy = " + misfirePolicy +
            ", concurrent = " + concurrent +
            ", status = " + status +
            ", remark = " + remark +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
    "}";
    }
}