package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色菜单 角色1-N菜单 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
	/**
	 * 查询表sys_role_menu所有信息
	 */
	List<SysRoleMenu> selectAllSysRoleMenu();

	/**
	 * 根据主键id查询表sys_role_menu信息
	 *
	 * @param id
	 */
	SysRoleMenu selectSysRoleMenuById(@Param("id") Long id);

	/**
	 * 根据条件查询表sys_role_menu信息
	 *
	 * @param sysRoleMenu
	 */
	List<SysRoleMenu> selectSysRoleMenuByCondition(SysRoleMenu sysRoleMenu);

	/**
	 * 根据主键id查询表sys_role_menu信息
	 *
	 * @param id
	 */
	Integer deleteSysRoleMenuById(@Param("id") Long id);

	/**
	 * 根据主键id更新表sys_role_menu信息
	 *
	 * @param sysRoleMenu
	 */
	Integer updateSysRoleMenuById(SysRoleMenu sysRoleMenu);

	/**
	 * 新增表sys_role_menu信息
	 *
	 * @param sysRoleMenu
	 */
	Integer insertSysRoleMenu(SysRoleMenu sysRoleMenu);
}
