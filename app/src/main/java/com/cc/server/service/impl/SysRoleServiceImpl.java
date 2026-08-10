package com.cc.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysRole;
import com.cc.core.mapper.SysRoleMapper;
import com.cc.core.service.SysRoleDepartmentService;
import com.cc.core.service.SysRoleMenuService;
import com.cc.core.service.SysRoleService;
import com.cc.core.service.SysUserRoleService;
import com.cc.core.vo.RoleVO;
import com.cc.framework.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuService roleMenuService;
    private final SysRoleDepartmentService roleDeptService;
    private final SysUserRoleService userRoleService;

    @Override
    @Transactional
    public boolean addRole(SysRole role, List<Long> menuIds) {
        // 校验角色编码唯一
        if (baseMapper.selectByCode(role.getCode()) != null) {
            throw ServiceException.badRequest("角色编码已存在");
        }
        save(role);
        // 分配菜单
        if (menuIds != null && !menuIds.isEmpty()) {
            roleMenuService.batchInsert(role.getId(), menuIds);
        }
        // 如果是自定义数据范围，分配部门
        if (role.getDataScope() != null && role.getDataScope() == 2) {
            // 部门分配由调用方通过 assignDept 单独处理
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateRole(SysRole role, List<Long> menuIds) {
        SysRole existing = getById(role.getId());
        if (existing == null) {
            throw ServiceException.notFound("角色不存在");
        }
        // 超级管理员角色不允许修改编码
        if ("admin".equals(existing.getCode()) && role.getCode() != null && !role.getCode().equals(existing.getCode())) {
            throw ServiceException.badRequest("超级管理员角色编码不允许修改");
        }
        updateById(role);
        // 重新分配菜单
        roleMenuService.deleteByRoleId(role.getId());
        if (menuIds != null && !menuIds.isEmpty()) {
            roleMenuService.batchInsert(role.getId(), menuIds);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean assignMenus(Long roleId, List<Long> menuIds) {
        if (getById(roleId) == null) {
            throw ServiceException.notFound("角色不存在");
        }
        roleMenuService.deleteByRoleId(roleId);
        if (menuIds != null && !menuIds.isEmpty()) {
            roleMenuService.batchInsert(roleId, menuIds);
        }
        return true;
    }

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        return baseMapper.selectRolesByUserId(userId);
    }

    /**
     * 查询所有正常角色
     */
    public List<SysRole> getAllEnabled() {
        return baseMapper.selectAllEnabled();
    }

    /**
     * 查询角色详情
     */
    public RoleVO getRoleDetail(Long roleId) {
        SysRole role = getById(roleId);
        if (role == null) {
            throw ServiceException.notFound("角色不存在");
        }
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);
        vo.setMenuIds(roleMenuService.getMenuIdsByRoleId(roleId));
        if (role.getDataScope() != null && role.getDataScope() == 2) {
            vo.setDeptIds(roleDeptService.getDeptIdsByRoleId(roleId));
        }
        return vo;
    }

    /**
     * 分配自定义数据权限部门
     */
    @Transactional
    public boolean assignDeptScope(Long roleId, List<Long> deptIds) {
        SysRole role = getById(roleId);
        if (role == null) {
            throw ServiceException.notFound("角色不存在");
        }
        roleDeptService.deleteByRoleId(roleId);
        if (deptIds != null && !deptIds.isEmpty()) {
            roleDeptService.batchInsert(roleId, deptIds);
        }
        return true;
    }

    /**
     * 删除角色（检查是否有关联用户）
     */
    @Transactional
    public boolean deleteRole(Long roleId) {
        SysRole role = getById(roleId);
        if (role == null) {
            throw ServiceException.notFound("角色不存在");
        }
        if ("admin".equals(role.getCode())) {
            throw ServiceException.badRequest("超级管理员角色不允许删除");
        }
        // 清除关联
        roleMenuService.deleteByRoleId(roleId);
        roleDeptService.deleteByRoleId(roleId);
        userRoleService.deleteByRoleId(roleId);
        return removeById(roleId);
    }
}
