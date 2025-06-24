package entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * sys_user_role 用户角色 用户N-1角色
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/

@Schema(name = "SysUserRoleVO对象", description = "用户角色 用户N-1角色")
public class SysUserRoleVO implements Serializable {

private static final long serialVersionUID = 1L;


            @Schema(description = "主键")
    private Long id;

            @Schema(description = "用户 id")
    private Long userId;

            @Schema(description = "角色 id")
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
    return "SysUserRoleVO{" +
            "id = " + id +
            ", userId = " + userId +
            ", roleId = " + roleId +
    "}";
    }
}