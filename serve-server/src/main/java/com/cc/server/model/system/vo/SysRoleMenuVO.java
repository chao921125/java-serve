package com.cc.server.model.system.vo;

import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_role_menu 角色菜单 角色1-N菜单
 * </p>
 * 
 * @author cc
 * @since 2024-12-05 10:57:08
 */

@Schema(name = "SysRoleMenuVO对象", description = "角色菜单 角色1-N菜单")
public class SysRoleMenuVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "主键")
    private Long id;

    @Schema(description = "-")
    private Long roleId;

    @Schema(description = "-")
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
    return "SysRoleMenuVO{" +
            "id = " + id +
            ", roleId = " + roleId +
            ", menuId = " + menuId +
    "}";
    }
}