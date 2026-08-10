package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户部门关联
 */
@Data
@EqualsAndHashCode
@TableName("sys_user_department")
public class SysUserDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户 ID */
    private Long userId;

    /** 部门 ID */
    private Long departmentId;
}
