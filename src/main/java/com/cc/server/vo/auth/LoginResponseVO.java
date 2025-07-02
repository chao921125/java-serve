package com.cc.server.vo.auth;

import com.cc.server.vo.system.SysUserVO;

public class LoginResponseVO {
    private String token;
    private SysUserVO user;

    public LoginResponseVO() {}
    public LoginResponseVO(String token, SysUserVO user) {
        this.token = token;
        this.user = user;
    }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public SysUserVO getUser() { return user; }
    public void setUser(SysUserVO user) { this.user = user; }
} 