package com.cc.serve.controller.system;

import com.cc.serve.entity.system.SysUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-11-22 13:40:55
 */
@Tag(name = "用户表", description = "用户表")
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @PostMapping("/login")
    public Object login() {
        return "";
    }

    public Object register(@RequestBody @Valid SysUser sysUser) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            sysUser.setPassword(encoder.encode(sysUser.getPassword()));
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    @PostMapping("/generateToken")
    public Object authenticateAndGetToken() {
        return "";
    }
}
