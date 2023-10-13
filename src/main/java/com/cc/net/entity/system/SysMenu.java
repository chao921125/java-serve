package com.cc.net.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author cc
 * @since 2023-47-13 15:10:968
 */
@Getter
@Setter
@TableName("sys_menu")
@Schema(description = "SysMenu对象")
public class SysMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "父级id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "菜单名称（国际化直接自动转化为对应的key）")
    @TableField("name")
    private String name;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "请求路径")
    @TableField("path")
    private String path;

    @Schema(description = "组件地址，默认在views/目录下，但不用填写views/")
    @TableField("component")
    private String component;

    @Schema(description = "图标")
    @TableField("icon")
    private String icon;

    @Schema(description = "标题")
    @TableField("title")
    private String title;

    @Schema(description = "菜单类型（0 菜单 1 目录）")
    @TableField("type")
    private String type;

    @Schema(description = "是否登录访问（默认此项不填 0否 1是）")
    @TableField("auth")
    private String auth;

    @Schema(description = "是否是连接（0否 1是）与iframe互斥")
    @TableField("isLink")
    private String isLink;

    @Schema(description = "是否是内嵌（0否 1是）与link互斥")
    @TableField("isIframe")
    private String isIframe;

    @Schema(description = "link或者iframe时，访问地址")
    @TableField("address")
    private String address;

    @Schema(description = "是否隐藏路由（0否 1是）")
    @TableField("isHide")
    private String isHide;

    @Schema(description = "是否隐藏子路由（0否 1是）")
    @TableField("isHideSubMenu")
    private String isHideSubMenu;

    @Schema(description = "是否为手机端（0否 1是）")
    @TableField("isMobile")
    private String isMobile;

    @Schema(description = "创建人")
    @TableField("create_by")
    private String createBy;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "修改人")
    @TableField("update_by")
    private String updateBy;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
