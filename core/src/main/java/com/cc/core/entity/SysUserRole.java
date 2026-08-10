package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户角色关联
 */
@Data
@EqualsAndHashCode
@TableName("sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户 ID */
    private Long userId;

    /** 角色 ID */
    private Long roleId;
}
