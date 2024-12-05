package com.cc.server.model.log.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * log_login 
 * </p>
 * 
 * @author cc
 * @since 2024-12-05 10:57:07
 */

@TableName("log_login")
public class LogLogin implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private String userId;

    @TableField("user_name")
    private String userName;

    @TableField("ip")
    private String ip;

    @TableField("ip_real")
    private String ipReal;

    @TableField("login_time")
    private Date loginTime;

    @TableField("address")
    private String address;

    @TableField("system")
    private String system;

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