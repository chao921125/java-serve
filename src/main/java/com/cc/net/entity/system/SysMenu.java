package com.cc.net.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author cc
 * @since 2023-55-27 14:09:404
 */
@Getter
@Setter
@TableName("sys_menu")
@ApiModel(value = "SysMenu对象", description = "菜单表")
public class SysMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("父级id")
    private Long parentId;

    @ApiModelProperty("菜单名称（国际化直接自动转化为对应的key）")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("请求路径")
    private String path;

    @ApiModelProperty("组件地址，默认在views/目录下，但不用填写views/")
    private String component;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("菜单类型（0 菜单 1 目录）")
    private String type;

    @ApiModelProperty("是否登录访问（默认此项不填 0否 1是）")
    private String auth;

    @ApiModelProperty("是否是连接（0否 1是）与iframe互斥")
    private String isLink;

    @ApiModelProperty("是否是内嵌（0否 1是）与link互斥")
    private String isIframe;

    @ApiModelProperty("link或者iframe时，访问地址")
    private String address;

    @ApiModelProperty("是否隐藏路由（0否 1是）")
    private String isHide;

    @ApiModelProperty("是否隐藏子路由（0否 1是）")
    private String isHideSubMenu;

    @ApiModelProperty("是否为手机端（0否 1是）")
    private String isMobile;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("备注")
    private String remark;
}
