package com.cc.server.controller.system;

import com.cc.server.model.system.entity.SysUser;
import com.cc.server.service.system.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "系统", description = "用户")
@RestController
@RequestMapping("/admin/sys-user")
public class SysUserController {
    @Resource
    private SysUserService userService;

    @Operation(summary = "获取用户信息", description = "用户名、邮箱、手机号，密码登录")
    @GetMapping("/user")
    public SysUser getUserByNameEmailPhone(@RequestParam String username) {
        return userService.getUserByNameEmailPhone(username);
    }
    @Operation(summary = "用户登录", description = "用户名、邮箱、手机号，密码登录")
    @Parameter(name = "username", description = "用户名", required = true)
    @Parameter(name = "password", description = "密码", required = true)
    @ApiResponse(responseCode = "200", description = "登录成功")
    @PostMapping("/login")
    public SysUser login(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }
}
