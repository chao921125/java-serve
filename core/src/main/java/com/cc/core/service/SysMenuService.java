package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysMenu;

import java.util.List;

/**
 * 菜单服务接口
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单树
     */
    List<SysMenu> getMenuTree();

    /**
     * 根据用户 ID 查询菜单树
     */
    List<SysMenu> getMenuTreeByUserId(Long userId);

    /**
     * 根据角色 ID 查询菜单 ID 列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);
}
