package com.cc.server.entity.system;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * sys_post 岗位表
 * </p>
 * 
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@TableName("sys_post")
public class SysPost implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("sort")
    private Integer sort;

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



    public SysPost(Long id,String code,String name,Integer sort,String status,String createBy,Date createTime,String updateBy,Date updateTime,String remark){
        this.id = id;
        this.code = code;
        this.name = name;
        this.sort = sort;
        this.status = status;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remark = remark;
    }

    public SysPost(){
    }

    @Override
    public String toString() {
        return "SysPost{" +
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