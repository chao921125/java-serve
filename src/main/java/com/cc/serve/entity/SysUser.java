package com.cc.serve.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author cc
 * @since 2024-09-28 15:03:228
 */
@TableName("sys_user")
@Schema(name = "SysUser对象", description = "用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "用户 id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "用户登陆名")
    @TableField("user_name")
    private String userName;

    @Schema(name = "登陆密码", pattern = "", maxLength = 20, minLength = 6)
    @TableField("password")
    private String password;

    @Schema(name = "昵称")
    @TableField("nick_name")
    private String nickName;

    @Schema(name = "真实姓名")
    @TableField("real_name")
    private String realName;

    @Schema(name = "头像")
    @TableField("avatar")
    private String avatar;

    @Schema(name = "邮箱")
    @TableField("email")
    private String email;

    @Schema(name = "手机号")
    @TableField("phone")
    private String phone;

    @Schema(name = "性别（0女 1男 2未知）")
    @TableField("sex")
    private String sex;

    @Schema(name = "年龄")
    @TableField("age")
    private String age;

    @Schema(name = "地址")
    @TableField("address")
    private String address;

    @Schema(name = "状态（1正常 0停用 9删除）")
    @TableField("status")
    private String status;

    @Schema(name = "用户类型（0 系统用户 1 2）")
    @TableField("type")
    private String type;

    @Schema(name = "最后登陆ip")
    @TableField("login_ip")
    private String loginIp;

    @Schema(name = "最后登陆地址")
    @TableField("login_address")
    private String loginAddress;

    @Schema(name = "最后登陆设备")
    @TableField("login_info")
    private String loginInfo;

    @Schema(name = "最后登陆时间")
    @TableField("login_time")
    private Date loginTime;

    @Schema(name = "密码最后修改时间")
    @TableField("pwd_update_time")
    private Date pwdUpdateTime;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
            ", type = " + type +
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
