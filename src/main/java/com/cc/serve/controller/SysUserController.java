package com.cc.serve.controller;

import com.cc.serve.entity.SysUser;
import com.cc.serve.service.SysUserService;
import com.github.pagehelper.PageHelper;
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
 * @since 2024-09-28 15:03:228
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {
    @Resource
    public SysUserService sysUserService;

    @GetMapping("/list")
    public List<SysUser> list() {
        return sysUserService.list();
    }

    public List<SysUser> listPage() {
        int pageSize = 10;
        int pageNumber = 1;
        int pageTotal;
        PageHelper.startPage(pageNumber, pageSize);
        return sysUserService.list();
    }
}
