package com.cc.server.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_department 部门表
 * </p>
 * 
 * @author cc
 * @since 2024-11-22 13:40:54
 */

@TableName("sys_department")
@Schema(name = "SysDepartment对象", description = "部门表")
public class SysDepartment implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "部门 id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "父级id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "祖级，树结构")
    @TableField("ancestors")
    private String ancestors;

    @Schema(description = "部门名称")
    @TableField("name")
    private String name;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "负责人")
    @TableField("leader")
    private String leader;

    @Schema(description = "手机号")
    @TableField("phone")
    private String phone;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

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

    public Long getParentId() {
    return parentId;
    }

    public void setParentId(Long parentId) {
    this.parentId = parentId;
    }

    public String getAncestors() {
    return ancestors;
    }

    public void setAncestors(String ancestors) {
    this.ancestors = ancestors;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public Integer getSort() {
    return sort;
    }

    public void setSort(Integer sort) {
    this.sort = sort;
    }

    public String getLeader() {
    return leader;
    }

    public void setLeader(String leader) {
    this.leader = leader;
    }

    public String getPhone() {
    return phone;
    }

    public void setPhone(String phone) {
    this.phone = phone;
    }

    public String getEmail() {
    return email;
    }

    public void setEmail(String email) {
    this.email = email;
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
    return "SysDepartment{" +
            "id = " + id +
            ", parentId = " + parentId +
            ", ancestors = " + ancestors +
            ", name = " + name +
            ", sort = " + sort +
            ", leader = " + leader +
            ", phone = " + phone +
            ", email = " + email +
            ", status = " + status +
            ", createBy = " + createBy +
            ", createTime = " + createTime +
            ", updateBy = " + updateBy +
            ", updateTime = " + updateTime +
            ", remark = " + remark +
    "}";
    }
}