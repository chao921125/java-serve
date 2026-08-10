package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色菜单关联
 */
@Data
@EqualsAndHashCode
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 角色 ID */
    private Long roleId;

    /** 菜单 ID */
    private Long menuId;
}
