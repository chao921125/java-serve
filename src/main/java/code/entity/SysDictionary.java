package entity;

import java.io.Serializable;
import java.util.Date;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import java.io.Serializable;
    import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * sys_dictionary 
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/
    @TableName("sys_dictionary")
public class SysDictionary implements Serializable {

private static final long serialVersionUID = 1L;


            @TableId("id")
    private Integer id;

        @TableField("name")
    private String name;

        @TableField("value")
    private String value;

        @TableField("status")
    private String status;

        @TableField("create_by")
    private String createBy;

        @TableField("create_time")
    private Date createTime;

        @TableField("update_by")
    private String updateBy;

        @TableField("update_time")
    private Date updateTime;

        @TableField("remark")
    private String remark;


        public Integer getId() {
        return id;
        }

            public void setId(Integer id) {
        this.id = id;
        }

        public String getName() {
        return name;
        }

            public void setName(String name) {
        this.name = name;
        }

        public String getValue() {
        return value;
        }

            public void setValue(String value) {
        this.value = value;
        }

        public String getStatus() {
        return status;
        }

            public void setStatus(String status) {
        this.status = status;
        }

        public String getCreateBy() {
        return createBy;
        }

            public void setCreateBy(String createBy) {
        this.createBy = createBy;
        }

        public Date getCreateTime() {
        return createTime;
        }

            public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        }

        public String getUpdateBy() {
        return updateBy;
        }

            public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        }

        public Date getUpdateTime() {
        return updateTime;
        }

            public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        }

        public String getRemark() {
        return remark;
        }

            public void setRemark(String remark) {
        this.remark = remark;
        }



public SysDictionary(Integer id,String name,String value,String status,String createBy,Date createTime,String updateBy,Date updateTime,String remark){
    this.id = id;
    this.name = name;
    this.value = value;
    this.status = status;
    this.createBy = createBy;
    this.createTime = createTime;
    this.updateBy = updateBy;
    this.updateTime = updateTime;
    this.remark = remark;
}

public SysDictionary(){
}

    @Override
    public String toString() {
    return "SysDictionary{" +
            "id = " + id +
            ", name = " + name +
            ", value = " + value +
            ", status = " + status +
            ", createBy = " + createBy +
            ", createTime = " + createTime +
            ", updateBy = " + updateBy +
            ", updateTime = " + updateTime +
            ", remark = " + remark +
    "}";
    }
}