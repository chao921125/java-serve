package com.cc.serve.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * <p>
 * 用户部门
 * </p>
 *
 * @author cc
 * @since 2024-09-28 15:03:231
 */
@TableName("sys_user_department")
@Schema(name = "SysUserDepartment对象", description = "用户部门")
public class SysUserDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "用户 id")
    @TableField("user_id")
    private Long userId;

    @Schema(name = "部门 id")
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
