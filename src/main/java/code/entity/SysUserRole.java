package entity;

import java.io.Serializable;
import java.util.Date;
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
* @since 2025-06-23 18:27:36
*/
    @TableName("sys_user_role")
public class SysUserRole implements Serializable {

private static final long serialVersionUID = 1L;


            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        @TableField("user_id")
    private Long userId;

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



public SysUserRole(Long id,Long userId,Long roleId){
    this.id = id;
    this.userId = userId;
    this.roleId = roleId;
}

public SysUserRole(){
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