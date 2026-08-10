package com.cc.core.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单树形视图对象
 */
@Data
public class MenuTreeVO {
    private Long id;
    private Long parentId;
    private String name;
    private Integer sort;
    private String path;
    private String component;
    private String icon;
    private String title;
    private String type;
    private String auth;
    private Boolean isLink;
    private Boolean isIframe;
    private String address;
    private Boolean isHide;
    private Boolean isHideSubMenu;
    private Boolean isMobile;
    private String createBy;
    private LocalDateTime createTime;
    private String remark;
    private List<MenuTreeVO> children;
}
