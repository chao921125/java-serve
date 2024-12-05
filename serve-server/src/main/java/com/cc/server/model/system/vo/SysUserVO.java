package com.cc.server.model.system.vo;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_user 用户表
 * </p>
 * 
 * @author cc
 * @since 2024-12-05 10:57:08
 */

@Schema(name = "SysUserVO对象", description = "用户表")
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "用户 id")
    private Long id;

    @Schema(description = "用户登陆名")
    private String userName;

    @Schema(description = "登陆密码")
    private String password;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别（0女 1男 2未知）")
    private String sex;

    @Schema(description = "年龄")
    private String age;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "状态（0正常 1停用 9删除）")
    private String status;

    @Schema(description = "最后登陆ip")
    private String loginIp;

    @Schema(description = "最后登陆地址")
    private String loginAddress;

    @Schema(description = "最后登陆设备")
    private String loginInfo;

    @Schema(description = "最后登陆时间")
    private Date loginTime;

    @Schema(description = "密码最后修改时间")
    private Date pwdUpdateTime;

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

    public String getUserName() {
    return userName;
    }

    public void setUserName(String userName) {
    this.userName = userName;
    }

    public String getPassword() {
    return password;
    }

    public void setPassword(String password) {
    this.password = password;
    }

    public String getNickName() {
    return nickName;
    }

    public void setNickName(String nickName) {
    this.nickName = nickName;
    }

    public String getRealName() {
    return realName;
    }

    public void setRealName(String realName) {
    this.realName = realName;
    }

    public String getAvatar() {
    return avatar;
    }

    public void setAvatar(String avatar) {
    this.avatar = avatar;
    }

    public String getEmail() {
    return email;
    }

    public void setEmail(String email) {
    this.email = email;
    }

    public String getPhone() {
    return phone;
    }

    public void setPhone(String phone) {
    this.phone = phone;
    }

    public String getSex() {
    return sex;
    }

    public void setSex(String sex) {
    this.sex = sex;
    }

    public String getAge() {
    return age;
    }

    public void setAge(String age) {
    this.age = age;
    }

    public String getAddress() {
    return address;
    }

    public void setAddress(String address) {
    this.address = address;
    }

    public String getStatus() {
    return status;
    }

    public void setStatus(String status) {
    this.status = status;
    }

    public String getLoginIp() {
    return loginIp;
    }

    public void setLoginIp(String loginIp) {
    this.loginIp = loginIp;
    }

    public String getLoginAddress() {
    return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
    this.loginAddress = loginAddress;
    }

    public String getLoginInfo() {
    return loginInfo;
    }

    public void setLoginInfo(String loginInfo) {
    this.loginInfo = loginInfo;
    }

    public Date getLoginTime() {
    return loginTime;
    }

    public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
    }

    public Date getPwdUpdateTime() {
    return pwdUpdateTime;
    }

    public void setPwdUpdateTime(Date pwdUpdateTime) {
    this.pwdUpdateTime = pwdUpdateTime;
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
    return "SysUserVO{" +
            "id = " + id +
            ", userName = " + userName +
            ", password = " + password +
            ", nickName = " + nickName +
            ", realName = " + realName +
            ", avatar = " + avatar +
            ", email = " + email +
            ", phone = " + phone +
            ", sex = " + sex +
            ", age = " + age +
            ", address = " + address +
            ", status = " + status +
            ", loginIp = " + loginIp +
            ", loginAddress = " + loginAddress +
            ", loginInfo = " + loginInfo +
            ", loginTime = " + loginTime +
            ", pwdUpdateTime = " + pwdUpdateTime +
            ", createBy = " + createBy +
            ", createTime = " + createTime +
            ", updateBy = " + updateBy +
            ", updateTime = " + updateTime +
            ", remark = " + remark +
    "}";
    }
}