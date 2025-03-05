package com.cc.server.vo.system;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_menu 菜单表
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */

@Schema(name = "SysMenuVO对象", description = "菜单表")
public class SysMenuVO implements Serializable {

	private static final long serialVersionUID = 1L;


	@Schema(description = "主键")
	private Long id;

	@Schema(description = "父级id")
	private Long parentId;

	@Schema(description = "菜单名称（国际化直接自动转化为对应的key）")
	private String name;

	@Schema(description = "排序")
	private Integer sort;

	@Schema(description = "请求路径")
	private String path;

	@Schema(description = "组件地址，默认在views/目录下，但不用填写views/")
	private String component;

	@Schema(description = "图标")
	private String icon;

	@Schema(description = "标题")
	private String title;

	@Schema(description = "菜单类型（0 菜单 1 目录）")
	private String type;

	@Schema(description = "是否登录访问（默认此项不填 0否 1是）")
	private String auth;

	@Schema(description = "是否是连接（0否 1是）与iframe互斥")
	private String isLink;

	@Schema(description = "是否是内嵌（0否 1是）与link互斥")
	private String isIframe;

	@Schema(description = "link或者iframe时，访问地址")
	private String address;

	@Schema(description = "是否隐藏路由（0否 1是）")
	private String isHide;

	@Schema(description = "是否隐藏子路由（0否 1是）")
	private String isHideSubMenu;

	@Schema(description = "是否为手机端（0否 1是）")
	private String isMobile;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "创建时间")
	private Date createTime;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "修改时间")
	private Date updateTime;

	@Schema(description = "备注")
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
		return "SysMenuVO{" +
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