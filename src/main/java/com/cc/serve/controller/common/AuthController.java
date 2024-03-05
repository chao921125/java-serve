package com.cc.serve.controller.common;

import com.cc.serve.common.core.entity.AjaxResult;
import com.cc.serve.entity.system.SysUser;
import com.cc.serve.service.common.AuthService;
import com.cc.serve.service.system.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AuthController {
    @Resource
    SysUserService sysUserService;
    @Resource
    AuthService authService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody SysUser user) {
        AjaxResult result = new AjaxResult();
        SysUser sysUser = sysUserService.selectByUser(user);
        return result;
    }
    @PostMapping("/register")
    public SysUser register(@RequestBody SysUser user) {
        SysUser sysUser = sysUserService.selectByUser(user);
        return sysUser;
    }
}
