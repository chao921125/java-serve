package com.cc.serve.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author cc
 * @since 2024-09-28 15:03:218
 */
@TableName("sys_menu")
@Schema(name = "SysMenu对象", description = "菜单表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "父级id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(name = "菜单名称（国际化直接自动转化为对应的key）")
    @TableField("name")
    private String name;

    @Schema(name = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(name = "请求路径")
    @TableField("path")
    private String path;

    @Schema(name = "组件地址，默认在views/目录下，但不用填写views/")
    @TableField("component")
    private String component;

    @Schema(name = "图标")
    @TableField("icon")
    private String icon;

    @Schema(name = "标题")
    @TableField("title")
    private String title;

    @Schema(name = "菜单类型（0 菜单 1 目录）")
    @TableField("type")
    private String type;

    @Schema(name = "是否登录访问（默认此项不填 0否 1是）")
    @TableField("auth")
    private String auth;

    @Schema(name = "是否是连接（0否 1是）与iframe互斥")
    @TableField("isLink")
    private String isLink;

    @Schema(name = "是否是内嵌（0否 1是）与link互斥")
    @TableField("isIframe")
    private String isIframe;

    @Schema(name = "link或者iframe时，访问地址")
    @TableField("address")
    private String address;

    @Schema(name = "是否隐藏路由（0否 1是）")
    @TableField("isHide")
    private String isHide;

    @Schema(name = "是否隐藏子路由（0否 1是）")
    @TableField("isHideSubMenu")
    private String isHideSubMenu;

    @Schema(name = "是否为手机端（0否 1是）")
    @TableField("isMobile")
    private String isMobile;

    @Schema(name = "创建人")
    @TableField("create_by")
    private String createBy;

    @Schema(name = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @Schema(name = "修改人")
    @TableField("update_by")
    private String updateBy;

    @Schema(name = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @Schema(name = "备注")
    @TableField("remark")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getIsLink() {
        return isLink;
    }

    public void setIsLink(String isLink) {
        this.isLink = isLink;
    }

    public String getIsIframe() {
        return isIframe;
    }

    public void setIsIframe(String isIframe) {
        this.isIframe = isIframe;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsHide() {
        return isHide;
    }

    public void setIsHide(String isHide) {
        this.isHide = isHide;
    }

    public String getIsHideSubMenu() {
        return isHideSubMenu;
    }

    public void setIsHideSubMenu(String isHideSubMenu) {
        this.isHideSubMenu = isHideSubMenu;
    }

    public String getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(String isMobile) {
        this.isMobile = isMobile;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
            "id = " + id +
            ", parentId = " + parentId +
            ", name = " + name +
            ", sort = " + sort +
            ", path = " + path +
            ", component = " + component +
            ", icon = " + icon +
            ", title = " + title +
            ", type = " + type +
            ", auth = " + auth +
            ", isLink = " + isLink +
            ", isIframe = " + isIframe +
            ", address = " + address +
            ", isHide = " + isHide +
            ", isHideSubMenu = " + isHideSubMenu +
            ", isMobile = " + isMobile +
            ", createBy = " + createBy +
            ", createTime = " + createTime +
            ", updateBy = " + updateBy +
            ", updateTime = " + updateTime +
            ", remark = " + remark +
            "}";
    }
}
