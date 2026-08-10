package com.cc.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.dto.AssignMenusDTO;
import com.cc.core.dto.RoleAddDTO;
import com.cc.core.entity.SysRole;
import com.cc.core.service.SysRoleService;
import com.cc.core.vo.RoleVO;
import com.cc.framework.annotation.Log;
import com.cc.framework.base.R;
import com.cc.framework.exception.ServiceException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;
    private final com.cc.server.service.impl.SysRoleServiceImpl roleServiceImpl;

    /**
     * 分页查询角色列表
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R<Page<SysRole>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {
        Page<SysRole> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(SysRole::getName, name);
        }
        wrapper.orderByAsc(SysRole::getSort);
        return R.ok(roleService.page(page, wrapper));
    }

    /**
     * 查询所有正常角色（下拉框）
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R<List<SysRole>> list() {
        return R.ok(roleServiceImpl.getAllEnabled());
    }

    /**
     * 查询角色详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:role:query')")
    public R<RoleVO> getInfo(@PathVariable Long id) {
        return R.ok(roleServiceImpl.getRoleDetail(id));
    }

    /**
     * 新增角色
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:add')")
    @Log(title = "角色管理", businessType = com.cc.core.enums.BusinessType.INSERT)
    public R<Void> add(@Valid @RequestBody RoleAddDTO dto) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(dto, role);
        roleService.addRole(role, dto.getMenuIds());
        return R.ok();
    }

    /**
     * 修改角色
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @Log(title = "角色管理", businessType = com.cc.core.enums.BusinessType.UPDATE)
    public R<Void> edit(@Valid @RequestBody RoleAddDTO dto) {
        if (dto.getId() == null) {
            throw ServiceException.badRequest("角色ID不能为空");
        }
        SysRole role = new SysRole();
        BeanUtils.copyProperties(dto, role);
        roleService.updateRole(role, dto.getMenuIds());
        return R.ok();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:role:remove')")
    @Log(title = "角色管理", businessType = com.cc.core.enums.BusinessType.DELETE)
    public R<Void> remove(@PathVariable Long id) {
        roleServiceImpl.deleteRole(id);
        return R.ok();
    }

    /**
     * 分配菜单权限
     */
    @PutMapping("/{id}/menus")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @Log(title = "角色管理", businessType = com.cc.core.enums.BusinessType.GRANT)
    public R<Void> assignMenus(@PathVariable Long id, @RequestBody AssignMenusDTO dto) {
        roleService.assignMenus(id, dto.getMenuIds());
        return R.ok();
    }

    /**
     * 分配数据权限部门
     */
    @PutMapping("/{id}/deptScope")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @Log(title = "角色管理", businessType = com.cc.core.enums.BusinessType.GRANT)
    public R<Void> assignDeptScope(@PathVariable Long id, @RequestBody AssignMenusDTO dto) {
        roleServiceImpl.assignDeptScope(id, dto.getMenuIds());
        return R.ok();
    }
}
