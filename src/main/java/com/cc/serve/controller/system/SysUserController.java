package com.cc.serve.controller.system;

import com.cc.serve.common.core.BaseController;
import com.cc.serve.common.core.ResultEntity;
import com.cc.serve.common.core.ResultPageEntity;
import com.cc.serve.entity.SysUser;
import com.cc.serve.service.system.SysUserService;
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
    public ResultPageEntity listPage(SysUser sysUser) {
        startPage();
//        List<SysUser> list = sysUserService.listPage();
        List<SysUser> list = sysUserService.list();
        return getDataTable(list);
    }

    @PostMapping
    public ResultEntity add(SysUser sysUser) {
        return toAjax(sysUserService.saveOrUpdate(sysUser));
    }

}
