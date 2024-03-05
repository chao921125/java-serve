package com.cc.serve.service.common.impl;

import com.cc.serve.entity.system.SysUser;
import com.cc.serve.mapper.system.SysUserMapper;
import com.cc.serve.service.common.AuthService;
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
