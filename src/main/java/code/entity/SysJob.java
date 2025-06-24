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
    * sys_job 
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/
    @TableName("sys_job")
public class SysJob implements Serializable {

private static final long serialVersionUID = 1L;


            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        @TableField("job_name")
    private String jobName;

        @TableField("job_group")
    private String jobGroup;

        @TableField("invoke_target")
    private String invokeTarget;

        @TableField("cron_expression")
    private String cronExpression;

        @TableField("misfire_policy")
    private String misfirePolicy;

        @TableField("concurrent")
    private String concurrent;

        @TableField("status")
    private String status;

        @TableField("remark")
    private String remark;

        @TableField("create_time")
    private Date createTime;

        @TableField("update_time")
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



public SysJob(Long id,String jobName,String jobGroup,String invokeTarget,String cronExpression,String misfirePolicy,String concurrent,String status,String remark,Date createTime,Date updateTime){
    this.id = id;
    this.jobName = jobName;
    this.jobGroup = jobGroup;
    this.invokeTarget = invokeTarget;
    this.cronExpression = cronExpression;
    this.misfirePolicy = misfirePolicy;
    this.concurrent = concurrent;
    this.status = status;
    this.remark = remark;
    this.createTime = createTime;
    this.updateTime = updateTime;
}

public SysJob(){
}

    @Override
    public String toString() {
    return "SysJob{" +
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