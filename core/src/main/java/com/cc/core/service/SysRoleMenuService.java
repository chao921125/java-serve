package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色菜单关联服务接口
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据角色 ID 查询菜单 ID 列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色 ID 删除所有关联
     */
    void deleteByRoleId(Long roleId);

    /**
     * 批量新增角色菜单关联
     */
    void batchInsert(Long roleId, List<Long> menuIds);
}
