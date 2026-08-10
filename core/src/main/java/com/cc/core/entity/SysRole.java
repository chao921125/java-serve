package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends BaseEntity {

    /** 角色名称 */
    private String name;

    /** 角色编码 */
    private String code;

    /** 排序 */
    private Integer sort;

    /** 权限标识列表（逗号分隔） */
    private String permissions;

    /** 数据范围 1-全部 2-自定义 3-本部门 4-本部门及下级 5-仅本人 */
    private Integer dataScope;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
