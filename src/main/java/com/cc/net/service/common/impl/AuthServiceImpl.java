package com.cc.net.service.common.impl;

import com.cc.net.entity.system.SysUser;
import com.cc.net.mapper.system.SysUserMapper;
import com.cc.net.service.common.AuthService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    SysUserMapper sysUserMapper;

    public SysUser login(SysUser user) {
        return null;
    }
}
