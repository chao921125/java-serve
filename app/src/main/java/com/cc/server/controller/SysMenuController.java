package com.cc.server.controller;

import com.cc.core.entity.SysMenu;
import com.cc.core.service.SysMenuService;
import com.cc.core.vo.MenuTreeVO;
import com.cc.framework.annotation.Log;
import com.cc.framework.base.R;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService menuService;
    private final com.cc.server.service.impl.SysMenuServiceImpl menuServiceImpl;

    /**
     * 查询菜单树
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public R<List<MenuTreeVO>> tree() {
        return R.ok(menuServiceImpl.getMenuTreeVO());
    }

    /**
     * 查询当前用户菜单树
     */
    @GetMapping("/userTree")
    public R<List<MenuTreeVO>> userTree() {
        Long userId = SecurityUtil.getUserId();
        return R.ok(menuServiceImpl.getMenuTreeVOByUserId(userId));
    }

    /**
     * 查询所有菜单（非树形，含按钮）
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public R<List<SysMenu>> list() {
        return R.ok(menuService.list());
    }

    /**
     * 查询菜单详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    public R<SysMenu> getInfo(@PathVariable Long id) {
        SysMenu menu = menuService.getById(id);
        return menu != null ? R.ok(menu) : R.fail("菜单不存在");
    }

    /**
     * 查询角色菜单 ID 列表
     */
    @GetMapping("/roleMenuIds/{roleId}")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    public R<List<Long>> roleMenuIds(@PathVariable Long roleId) {
        return R.ok(menuService.getMenuIdsByRoleId(roleId));
    }

    /**
     * 新增菜单
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @Log(title = "菜单管理", businessType = com.cc.core.enums.BusinessType.INSERT)
    public R<Void> add(@RequestBody SysMenu menu) {
        menuServiceImpl.addMenu(menu);
        return R.ok();
    }

    /**
     * 修改菜单
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    @Log(title = "菜单管理", businessType = com.cc.core.enums.BusinessType.UPDATE)
    public R<Void> edit(@RequestBody SysMenu menu) {
        menuServiceImpl.updateMenu(menu);
        return R.ok();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:menu:remove')")
    @Log(title = "菜单管理", businessType = com.cc.core.enums.BusinessType.DELETE)
    public R<Void> remove(@PathVariable Long id) {
        menuServiceImpl.deleteMenu(id);
        return R.ok();
    }
}
