package com.cc.server.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.server.entity.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	/**
	 * 查询表sys_menu所有信息
	 */
	List<SysMenu> selectAllSysMenu();

	/**
	 * 根据主键id查询表sys_menu信息
	 *
	 * @param id
	 */
	SysMenu selectSysMenuById(@Param("id") Long id);

	/**
	 * 根据条件查询表sys_menu信息
	 *
	 * @param sysMenu
	 */
	List<SysMenu> selectSysMenuByCondition(SysMenu sysMenu);

	/**
	 * 根据主键id查询表sys_menu信息
	 *
	 * @param id
	 */
	Integer deleteSysMenuById(@Param("id") Long id);

	/**
	 * 根据主键id更新表sys_menu信息
	 *
	 * @param sysMenu
	 */
	Integer updateSysMenuById(SysMenu sysMenu);

	/**
	 * 新增表sys_menu信息
	 *
	 * @param sysMenu
	 */
	Integer insertSysMenu(SysMenu sysMenu);

}
