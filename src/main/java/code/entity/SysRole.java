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
    * sys_role 角色表
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/
    @TableName("sys_role")
public class SysRole implements Serializable {

private static final long serialVersionUID = 1L;


            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        @TableField("name")
    private String name;

        @TableField("code")
    private String code;

        @TableField("sort")
    private Integer sort;

        @TableField("permissions")
    private String permissions;

        @TableField("data_scope")
    private String dataScope;

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


        public Long getId() {
        return id;
        }

            public void setId(Long id) {
        this.id = id;
        }

        public String getName() {
        return name;
        }

            public void setName(String name) {
        this.name = name;
        }

        public String getCode() {
        return code;
        }

            public void setCode(String code) {
        this.code = code;
        }

        public Integer getSort() {
        return sort;
        }

            public void setSort(Integer sort) {
        this.sort = sort;
        }

        public String getPermissions() {
        return permissions;
        }

            public void setPermissions(String permissions) {
        this.permissions = permissions;
        }

        public String getDataScope() {
        return dataScope;
        }

            public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
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



public SysRole(Long id,String name,String code,Integer sort,String permissions,String dataScope,String status,String createBy,Date createTime,String updateBy,Date updateTime,String remark){
    this.id = id;
    this.name = name;
    this.code = code;
    this.sort = sort;
    this.permissions = permissions;
    this.dataScope = dataScope;
    this.status = status;
    this.createBy = createBy;
    this.createTime = createTime;
    this.updateBy = updateBy;
    this.updateTime = updateTime;
    this.remark = remark;
}

public SysRole(){
}

    @Override
    public String toString() {
    return "SysRole{" +
            "id = " + id +
            ", name = " + name +
            ", code = " + code +
            ", sort = " + sort +
            ", permissions = " + permissions +
            ", dataScope = " + dataScope +
            ", status = " + status +
            ", createBy = " + createBy +
            ", createTime = " + createTime +
            ", updateBy = " + updateBy +
            ", updateTime = " + updateTime +
            ", remark = " + remark +
    "}";
    }
}