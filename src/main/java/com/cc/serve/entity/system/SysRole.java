package com.cc.serve.entity.system;

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
 * @since 2024-11-22 13:40:55
 */

@TableName("sys_role")
@Schema(name = "SysRole对象", description = "角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "角色 id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "角色key")
    @TableField("code")
    private String code;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "权限标识[C,R,U,D]")
    @TableField("permissions")
    private String permissions;

    @Schema(description = "数据权限（1全部 2当前及以下 3当前）")
    @TableField("data_scope")
    private String dataScope;

    @Schema(description = "状态（0正常 1停用 9删除）")
    @TableField("status")
    private String status;

    @Schema(description = "创建人")
    @TableField("create_by")
    private String createBy;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "修改人")
    @TableField("update_by")
    private String updateBy;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @Schema(description = "备注")
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