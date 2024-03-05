package com.cc.serve.controller.system;

import com.cc.serve.entity.system.SysUser;
import com.cc.serve.service.system.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2023-47-13 15:10:984
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Resource
    SysUserService sysUserService;

    @GetMapping("/all")
    public List<SysUser> selectListAll() {
        return sysUserService.selectListAll();
    }
}
