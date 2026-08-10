package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysUserRole;

import java.util.List;

/**
 * 用户角色关联服务接口
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户 ID 查询角色 ID 列表
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 根据用户 ID 删除所有关联
     */
    void deleteByUserId(Long userId);

    /**
     * 根据角色 ID 删除所有关联
     */
    void deleteByRoleId(Long roleId);

    /**
     * 批量新增用户角色关联
     */
    void batchInsert(Long userId, List<Long> roleIds);
}
