package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单 Mapper
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户 ID 查询菜单列表
     */
    @Select("""
            SELECT DISTINCT m.* FROM sys_menu m
            LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id
            LEFT JOIN sys_user_role ur ON rm.role_id = ur.role_id
            WHERE ur.user_id = #{userId}
            ORDER BY m.sort ASC
            """)
    List<SysMenu> selectMenusByUserId(@Param("userId") Long userId);

    /**
     * 根据角色 ID 查询菜单列表
     */
    @Select("""
            SELECT m.* FROM sys_menu m
            LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id
            WHERE rm.role_id = #{roleId}
            ORDER BY m.sort ASC
            """)
    List<SysMenu> selectMenusByRoleId(@Param("roleId") Long roleId);

    /**
     * 查询所有菜单（树形排序）
     */
    @Select("SELECT * FROM sys_menu ORDER BY parent_id ASC, sort ASC")
    List<SysMenu> selectAllOrdered();

    /**
     * 根据角色 ID 查询菜单 ID 列表
     */
    @Select("SELECT menu_id FROM sys_role_menu WHERE role_id = #{roleId}")
    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 查询所有权限标识
     */
    @Select("SELECT DISTINCT auth FROM sys_menu WHERE auth IS NOT NULL AND auth != '' AND deleted = 0")
    List<String> selectAllPermissions();
}
