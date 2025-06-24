package entity;

import java.io.Serializable;
import java.util.Date;
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
* @since 2025-06-23 18:27:36
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