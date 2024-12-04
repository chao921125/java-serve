package com.cc.serveserver.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_user_department 用户部门 用户1-1 部门
 * </p>
 * 
 * @author cc
 * @since 2024-11-22 13:40:55
 */

@TableName("sys_user_department")
@Schema(name = "SysUserDepartment对象", description = "用户部门 用户1-1 部门")
public class SysUserDepartment implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户 id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "部门 id")
    @TableField("department_id")
    private Long departmentId;


    public Long getId() {
    return id;
    }

    public void setId(Long id) {
    this.id = id;
    }

    public Long getUserId() {
    return userId;
    }

    public void setUserId(Long userId) {
    this.userId = userId;
    }

    public Long getDepartmentId() {
    return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
    }

    @Override
    public String toString() {
    return "SysUserDepartment{" +
            "id = " + id +
            ", userId = " + userId +
            ", departmentId = " + departmentId +
    "}";
    }
}