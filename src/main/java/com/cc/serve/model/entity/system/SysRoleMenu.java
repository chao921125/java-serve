package com.cc.serve.model.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_role_menu 角色菜单 角色1-N菜单
 * </p>
 * 
 * @author cc
 * @since 2024-11-22 13:40:55
 */

@TableName("sys_role_menu")
@Schema(name = "SysRoleMenu对象", description = "角色菜单 角色1-N菜单")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "主键")
    @TableId("id")
    private Long id;

    @Schema(description = "-")
    @TableField("role_id")
    private Long roleId;

    @Schema(description = "-")
    @TableField("menu_id")
    private Long menuId;


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

    public Long getMenuId() {
    return menuId;
    }

    public void setMenuId(Long menuId) {
    this.menuId = menuId;
    }

    @Override
    public String toString() {
    return "SysRoleMenu{" +
            "id = " + id +
            ", roleId = " + roleId +
            ", menuId = " + menuId +
    "}";
    }
}