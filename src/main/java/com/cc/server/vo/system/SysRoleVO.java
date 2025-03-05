package com.cc.server.vo.system;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_role 角色表
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */

@Schema(name = "SysRoleVO对象", description = "角色表")
public class SysRoleVO implements Serializable {

	private static final long serialVersionUID = 1L;


	@Schema(description = "角色 id")
	private Long id;

	@Schema(description = "名称")
	private String name;

	@Schema(description = "角色key")
	private String code;

	@Schema(description = "排序")
	private Integer sort;

	@Schema(description = "权限标识[C,R,U,D]")
	private String permissions;

	@Schema(description = "数据权限（1全部 2当前及以下 3当前）")
	private String dataScope;

	@Schema(description = "状态（0正常 1停用 9删除）")
	private String status;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "SysRoleVO{" +
				"id = " + id +
				", name = " + name +
				", code = " + code +
				", sort = " + sort +
				", permissions = " + permissions +
				", dataScope = " + dataScope +
				", status = " + status +
				", createBy = " + createBy +
				", createTime = " + createTime +
				", updateBy = " + updateBy +
				", updateTime = " + updateTime +
				", remark = " + remark +
				"}";
	}
}