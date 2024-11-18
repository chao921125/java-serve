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
 * @since 2024-09-28 15:03:227
 */
@TableName("sys_role_menu")
@Schema(name = "SysRoleMenu对象", description = "角色菜单")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @TableField("role_id")
    private Long roleId;

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
