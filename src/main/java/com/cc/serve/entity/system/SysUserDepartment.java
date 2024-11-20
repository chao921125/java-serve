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

    /**
     * <p>
     * sys_user_department 用户部门
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_user_department")
    @Schema(name = "SysUserDepartment对象", description = "用户部门")
    public static class SysUserDepartment implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "主键")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @Schema(description = "用户 id")
        @TableId("user_id")
        private Long userId;

        @Schema(description = "部门 id")
        @TableId("department_id")
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

    /**
     * <p>
     * sys_user_post 用户岗位
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_user_post")
    @Schema(name = "SysUserPost对象", description = "用户岗位")
    public static class SysUserPost implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "主键")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @Schema(description = "用户 id")
        @TableId("user_id")
        private Long userId;

        @Schema(description = "岗位 id")
        @TableId("post_id")
        private Long postId;


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

        public Long getPostId() {
        return postId;
        }

        public void setPostId(Long postId) {
        this.postId = postId;
        }

        @Override
        public String toString() {
        return "SysUserPost{" +
                "id = " + id +
                ", userId = " + userId +
                ", postId = " + postId +
        "}";
        }
    }

    /**
     * <p>
     * sys_user_role 用户角色
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_user_role")
    @Schema(name = "SysUserRole对象", description = "用户角色")
    public static class SysUserRole implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "主键")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @Schema(description = "用户 id")
        @TableId("user_id")
        private Long userId;

        @Schema(description = "角色 id")
        @TableId("role_id")
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
}
