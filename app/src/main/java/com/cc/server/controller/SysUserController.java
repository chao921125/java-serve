package com.cc.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.dto.AssignRolesDTO;
import com.cc.core.dto.ResetPasswordDTO;
import com.cc.core.dto.UserAddDTO;
import com.cc.core.dto.UserEditDTO;
import com.cc.core.entity.SysUser;
import com.cc.core.service.SysUserService;
import com.cc.core.vo.UserVO;
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
 * 用户管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public R<Page<SysUser>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long deptId) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (userName != null && !userName.isEmpty()) {
            wrapper.like(SysUser::getUserName, userName);
        }
        if (status != null) {
            wrapper.eq(SysUser::getStatus, status);
        }
        if (deptId != null) {
            wrapper.eq(SysUser::getDeptId, deptId);
        }
        wrapper.orderByDesc(SysUser::getCreateTime);
        Page<SysUser> result = userService.page(page, wrapper);
        // 清除密码
        result.getRecords().forEach(u -> u.setPassword(null));
        return R.ok(result);
    }

    /**
     * 查询用户详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public R<UserVO> getInfo(@PathVariable Long id) {
        SysUser user = userService.getById(id);
        if (user == null) {
            throw ServiceException.notFound("用户不存在");
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return R.ok(vo);
    }

    /**
     * 新增用户
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:add')")
    @Log(title = "用户管理", businessType = com.cc.core.enums.BusinessType.INSERT)
    public R<Void> add(@Valid @RequestBody UserAddDTO dto) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);
        userService.addUser(user, dto.getRoleIds(), dto.getDeptId());
        return R.ok();
    }

    /**
     * 修改用户
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @Log(title = "用户管理", businessType = com.cc.core.enums.BusinessType.UPDATE)
    public R<Void> edit(@Valid @RequestBody UserEditDTO dto) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);
        userService.updateUser(user, dto.getRoleIds(), dto.getDeptId());
        return R.ok();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:user:remove')")
    @Log(title = "用户管理", businessType = com.cc.core.enums.BusinessType.DELETE)
    public R<Void> remove(@PathVariable Long id) {
        if (id == 1L) {
            throw ServiceException.badRequest("超级管理员不允许删除");
        }
        userService.removeById(id);
        return R.ok();
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('sys:user:remove')")
    @Log(title = "用户管理", businessType = com.cc.core.enums.BusinessType.DELETE)
    public R<Void> removeBatch(@RequestBody List<Long> ids) {
        if (ids.contains(1L)) {
            throw ServiceException.badRequest("超级管理员不允许删除");
        }
        userService.removeByIds(ids);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @PutMapping("/{id}/resetPwd")
    @PreAuthorize("hasAuthority('sys:user:resetPwd')")
    @Log(title = "用户管理", businessType = com.cc.core.enums.BusinessType.UPDATE)
    public R<Void> resetPwd(@PathVariable Long id, @Valid @RequestBody ResetPasswordDTO dto) {
        userService.resetPassword(id, dto.getNewPassword());
        return R.ok();
    }

    /**
     * 修改状态
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @Log(title = "用户管理", businessType = com.cc.core.enums.BusinessType.UPDATE)
    public R<Void> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        userService.updateById(user);
        return R.ok();
    }

    /**
     * 分配角色
     */
    @PutMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @Log(title = "用户管理", businessType = com.cc.core.enums.BusinessType.GRANT)
    public R<Void> assignRoles(@PathVariable Long id, @RequestBody AssignRolesDTO dto) {
        userService.assignRoles(id, dto.getRoleIds());
        return R.ok();
    }
}
