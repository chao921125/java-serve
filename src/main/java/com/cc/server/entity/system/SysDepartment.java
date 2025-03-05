package com.cc.server.entity.system;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * sys_department 部门表
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@TableName("sys_department")
public class SysDepartment implements Serializable {

	private static final long serialVersionUID = 1L;


	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@TableField("parent_id")
	private Long parentId;

	@TableField("ancestors")
	private String ancestors;

	@TableField("name")
	private String name;

	@TableField("sort")
	private Integer sort;

	@TableField("leader")
	private String leader;

	@TableField("phone")
	private String phone;

	@TableField("email")
	private String email;

	@TableField("status")
	private String status;

	@TableField("create_by")
	private String createBy;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_by")
	private String updateBy;

	@TableField("update_time")
	private Date updateTime;

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

	public String getAncestors() {
		return ancestors;
	}

	public void setAncestors(String ancestors) {
		this.ancestors = ancestors;
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

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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


	public SysDepartment(Long id, Long parentId, String ancestors, String name, Integer sort, String leader, String phone, String email, String status, String createBy, Date createTime, String updateBy, Date updateTime, String remark) {
		this.id = id;
		this.parentId = parentId;
		this.ancestors = ancestors;
		this.name = name;
		this.sort = sort;
		this.leader = leader;
		this.phone = phone;
		this.email = email;
		this.status = status;
		this.createBy = createBy;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
		this.remark = remark;
	}

	public SysDepartment() {
	}

	@Override
	public String toString() {
		return "SysDepartment{" +
				"id = " + id +
				", parentId = " + parentId +
				", ancestors = " + ancestors +
				", name = " + name +
				", sort = " + sort +
				", leader = " + leader +
				", phone = " + phone +
				", email = " + email +
				", status = " + status +
				", createBy = " + createBy +
				", createTime = " + createTime +
				", updateBy = " + updateBy +
				", updateTime = " + updateTime +
				", remark = " + remark +
				"}";
	}
}