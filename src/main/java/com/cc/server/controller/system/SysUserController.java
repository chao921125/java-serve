package com.cc.server.controller.system;

import com.cc.server.model.system.entity.SysUser;
import com.cc.server.service.system.SysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "用户表", description = "用户表")
@RestController
@RequestMapping("/sys-user")
public class SysUserController {
    @Resource
    private SysUserService userService;

    @Tag(name = "获取用户信息", description = "获取用户信息")
    @GetMapping("/user")
    public SysUser getUserByNameEmailPhone(@RequestParam String username) {
        return userService.getUserByNameEmailPhone(username);
    }
}
