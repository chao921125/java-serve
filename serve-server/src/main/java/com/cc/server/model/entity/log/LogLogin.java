package com.cc.server.model.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * log_login 
 * </p>
 * 
 * @author cc
 * @since 2024-11-22 13:40:54
 */

@TableName("log_login")
@Schema(name = "LogLogin对象", description = "")
public class LogLogin implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户 id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "用户名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "登录 IP")
    @TableField("ip")
    private String ip;

    @Schema(description = "真实IP")
    @TableField("ip_real")
    private String ipReal;

    @Schema(description = "登录时间")
    @TableField("login_time")
    private Date loginTime;

    @Schema(description = "地址")
    @TableField("address")
    private String address;

    @Schema(description = "设备信息")
    @TableField("system")
    private String system;

    @Schema(description = "登录状态（0 失败，1 成功）")
    @TableField("status")
    private String status;


    public Long getId() {
    return id;
    }

    public void setId(Long id) {
    this.id = id;
    }

    public String getUserId() {
    return userId;
    }

    public void setUserId(String userId) {
    this.userId = userId;
    }

    public String getUserName() {
    return userName;
    }

    public void setUserName(String userName) {
    this.userName = userName;
    }

    public String getIp() {
    return ip;
    }

    public void setIp(String ip) {
    this.ip = ip;
    }

    public String getIpReal() {
    return ipReal;
    }

    public void setIpReal(String ipReal) {
    this.ipReal = ipReal;
    }

    public Date getLoginTime() {
    return loginTime;
    }

    public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
    }

    public String getAddress() {
    return address;
    }

    public void setAddress(String address) {
    this.address = address;
    }

    public String getSystem() {
    return system;
    }

    public void setSystem(String system) {
    this.system = system;
    }

    public String getStatus() {
    return status;
    }

    public void setStatus(String status) {
    this.status = status;
    }

    @Override
    public String toString() {
    return "LogLogin{" +
            "id = " + id +
            ", userId = " + userId +
            ", userName = " + userName +
            ", ip = " + ip +
            ", ipReal = " + ipReal +
            ", loginTime = " + loginTime +
            ", address = " + address +
            ", system = " + system +
            ", status = " + status +
    "}";
    }
}