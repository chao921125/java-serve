package com.cc.serve.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单
 * </p>
 *
 * @author cc
 * @since 2024-09-28 15:03:226
 */
@TableName("sys_role_department")
@Schema(name = "SysRoleDepartment对象", description = "角色菜单")
public class SysRoleDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @TableField("role_id")
    private Long roleId;

    @TableField("department_id")
    private Long departmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "SysRoleDepartment{" +
            "id = " + id +
            ", roleId = " + roleId +
            ", departmentId = " + departmentId +
            "}";
    }
}
