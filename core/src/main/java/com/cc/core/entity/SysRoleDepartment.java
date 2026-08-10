package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色部门关联（自定义数据权限）
 */
@Data
@EqualsAndHashCode
@TableName("sys_role_department")
public class SysRoleDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 角色 ID */
    private Long roleId;

    /** 部门 ID */
    private Long departmentId;
}
