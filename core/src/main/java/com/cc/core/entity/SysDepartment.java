package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统部门
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_department")
public class SysDepartment extends BaseEntity {

    /** 父部门 ID */
    private Long parentId;

    /** 祖级列表（逗号分隔的 ID，如 0,100,200） */
    private String ancestors;

    /** 部门名称 */
    private String name;

    /** 排序 */
    private Integer sort;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 状态 0-正常 1-停用 */
    private Integer status;

    /** 子部门列表（非表字段，仅用于树形展示） */
    @TableField(exist = false)
    private List<SysDepartment> children;
}
