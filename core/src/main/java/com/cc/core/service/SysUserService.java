package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysUser;

import java.util.List;

/**
 * 用户服务接口
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询
     */
    SysUser getByUsername(String username);

    /**
     * 新增用户（含角色分配）
     */
    boolean addUser(SysUser user, List<Long> roleIds, Long deptId);

    /**
     * 修改用户（含角色分配）
     */
    boolean updateUser(SysUser user, List<Long> roleIds, Long deptId);

    /**
     * 重置密码
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * 修改密码
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 分配角色
     */
    boolean assignRoles(Long userId, List<Long> roleIds);
}
