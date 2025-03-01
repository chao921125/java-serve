package com.cc.server.entity.system;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * sys_role_menu 角色菜单 角色1-N菜单
 * </p>
 * 
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@TableName("sys_role_menu")
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



    public SysRoleMenu(Long id,Long roleId,Long menuId){
        this.id = id;
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public SysRoleMenu(){
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