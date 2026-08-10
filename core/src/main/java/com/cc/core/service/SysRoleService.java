package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysRole;

import java.util.List;

/**
 * 角色服务接口
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 新增角色（含菜单分配）
     */
    boolean addRole(SysRole role, List<Long> menuIds);

    /**
     * 修改角色（含菜单分配）
     */
    boolean updateRole(SysRole role, List<Long> menuIds);

    /**
     * 分配菜单权限
     */
    boolean assignMenus(Long roleId, List<Long> menuIds);

    /**
     * 根据用户 ID 查询角色列表
     */
    List<SysRole> getRolesByUserId(Long userId);
}
