package com.cc.server.controller.system;

import com.cc.server.entity.system.SysRole;
import com.cc.server.service.system.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "系统", description = "角色")
@RestController
@RequestMapping("/admin/sys-role")
public class SysRoleController {
    @Resource
    private SysRoleService roleService;

    @Operation(summary = "获取角色列表", description = "角色")
    @GetMapping("/list")
    public List<SysRole> getRoleById() {
        return roleService.selectAllSysRole();
    }
}
