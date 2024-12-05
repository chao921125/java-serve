package com.cc.server.model.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * sys_user_department 用户部门 用户1-1 部门
 * </p>
 * 
 * @author cc
 * @since 2024-12-05 10:57:08
 */

@TableName("sys_user_department")
public class SysUserDepartment implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

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