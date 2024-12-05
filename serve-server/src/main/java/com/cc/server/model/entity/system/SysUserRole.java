package com.cc.server.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_user_role 用户角色 用户N-1角色
 * </p>
 * 
 * @author cc
 * @since 2024-11-22 13:40:55
 */

@TableName("sys_user_role")
@Schema(name = "SysUserRole对象", description = "用户角色 用户N-1角色")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户 id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "角色 id")
    @TableField("role_id")
    private Long roleId;


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

    public Long getRoleId() {
    return roleId;
    }

    public void setRoleId(Long roleId) {
    this.roleId = roleId;
    }

    @Override
    public String toString() {
    return "SysUserRole{" +
            "id = " + id +
            ", userId = " + userId +
            ", roleId = " + roleId +
    "}";
    }
}