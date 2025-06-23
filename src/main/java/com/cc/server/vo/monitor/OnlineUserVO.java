package com.cc.server.vo.monitor;

import java.io.Serializable;
import java.util.Date;

public class OnlineUserVO implements Serializable {
    private Long userId;
    private String userName;
    private String token;
    private Date loginTime;
    private Date lastActiveTime;
    private String ip;
    private String status;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Date getLoginTime() { return loginTime; }
    public void setLoginTime(Date loginTime) { this.loginTime = loginTime; }
    public Date getLastActiveTime() { return lastActiveTime; }
    public void setLastActiveTime(Date lastActiveTime) { this.lastActiveTime = lastActiveTime; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 