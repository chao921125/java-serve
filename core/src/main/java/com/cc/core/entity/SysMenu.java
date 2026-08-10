package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统菜单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    /** 父菜单 ID */
    private Long parentId;

    /** 菜单名称 */
    private String name;

    /** 排序 */
    private Integer sort;

    /** 路由路径 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 菜单图标 */
    private String icon;

    /** 菜单标题 */
    private String title;

    /** 菜单类型 M-目录 C-菜单 F-按钮 */
    private String type;

    /** 权限标识（如 sys:user:list） */
    private String auth;

    /** 是否外链 */
    private Boolean isLink;

    /** 是否内嵌（iframe） */
    private Boolean isIframe;

    /** 内嵌地址 */
    private String address;

    /** 是否隐藏 */
    private Boolean isHide;

    /** 是否隐藏子菜单 */
    private Boolean isHideSubMenu;

    /** 是否移动端 */
    private Boolean isMobile;

    /** 子菜单列表（非表字段，仅用于树形展示） */
    @TableField(exist = false)
    private List<SysMenu> children;
}
