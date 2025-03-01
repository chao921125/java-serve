package com.cc.server.entity.system;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * sys_user 用户表
 * </p>
 * 
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("nick_name")
    private String nickName;

    @TableField("real_name")
    private String realName;

    @TableField("avatar")
    private String avatar;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("sex")
    private String sex;

    @TableField("age")
    private String age;

    @TableField("address")
    private String address;

    @TableField("status")
    private String status;

    @TableField("login_ip")
    private String loginIp;

    @TableField("login_address")
    private String loginAddress;

    @TableField("login_info")
    private String loginInfo;

    @TableField("login_time")
    private Date loginTime;

    @TableField("pwd_update_time")
    private Date pwdUpdateTime;

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



    public SysUser(Long id,String userName,String password,String nickName,String realName,String avatar,String email,String phone,String sex,String age,String address,String status,String loginIp,String loginAddress,String loginInfo,Date loginTime,Date pwdUpdateTime,String createBy,Date createTime,String updateBy,Date updateTime,String remark){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.realName = realName;
        this.avatar = avatar;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.status = status;
        this.loginIp = loginIp;
        this.loginAddress = loginAddress;
        this.loginInfo = loginInfo;
        this.loginTime = loginTime;
        this.pwdUpdateTime = pwdUpdateTime;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remark = remark;
    }

    public SysUser(){
    }

    @Override
    public String toString() {
        return "SysUser{" +
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