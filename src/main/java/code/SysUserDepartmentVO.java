package entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * sys_user_department 用户部门 用户1-1 部门
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/

@Schema(name = "SysUserDepartmentVO对象", description = "用户部门 用户1-1 部门")
public class SysUserDepartmentVO implements Serializable {

private static final long serialVersionUID = 1L;


            @Schema(description = "主键")
    private Long id;

            @Schema(description = "用户 id")
    private Long userId;

            @Schema(description = "部门 id")
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
    return "SysUserDepartmentVO{" +
            "id = " + id +
            ", userId = " + userId +
            ", departmentId = " + departmentId +
    "}";
    }
}