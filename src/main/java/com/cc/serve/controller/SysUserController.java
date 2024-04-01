package com.cc.serve.controller;

import com.cc.serve.common.BaseController;
import com.cc.serve.common.ResultEntity;
import com.cc.serve.entity.SysUser;
import com.cc.serve.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class SysUserController extends BaseController {
    @Resource
    public SysUserService sysUserService;

    @GetMapping("/list")
    public List<SysUser> list() {
        return sysUserService.list();
    }

    @GetMapping("/page")
    public List<SysUser> listPage() {
        startPage();
        return sysUserService.list();
    }

    @PostMapping
    public ResultEntity<SysUser> add(SysUser sysUser) {
        ResultEntity<SysUser> resultEntity = new ResultEntity<SysUser>();
        boolean isSuccess = sysUserService.saveOrUpdate(sysUser);
        return resultEntity;
    }
}
