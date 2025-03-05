package com.cc.server.vo.system;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_post 岗位表
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */

@Schema(name = "SysPostVO对象", description = "岗位表")
public class SysPostVO implements Serializable {

	private static final long serialVersionUID = 1L;


	@Schema(description = "岗位 id")
	private Long id;

	@Schema(description = "key")
	private String code;

	@Schema(description = "名称")
	private String name;

	@Schema(description = "排序")
	private Integer sort;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
		return "SysPostVO{" +
				"id = " + id +
				", code = " + code +
				", name = " + name +
				", sort = " + sort +
				", status = " + status +
				", createBy = " + createBy +
				", createTime = " + createTime +
				", updateBy = " + updateBy +
				", updateTime = " + updateTime +
				", remark = " + remark +
				"}";
	}
}