package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysMenuService extends IService<SysMenu> {
  /**
  *  查询表sys_menu所有信息
  */
  List<SysMenu> selectAllSysMenu();

   /**
   *  根据主键id查询表sys_menu信息
   *  @param id
   */
   SysMenu selectSysMenuById(@Param("id") Long id);

  /**
  *  根据条件查询表sys_menu信息
  *  @param sysMenu
  */
  List<SysMenu> selectSysMenuByCondition(SysMenu sysMenu);

   /**
   *  根据主键id查询表sys_menu信息
   *  @param id
   */
   Integer deleteSysMenuById(@Param("id") Long id);

   /**
   *  根据主键id更新表sys_menu信息
   *  @param sysMenu
   */
   Integer updateSysMenuById(SysMenu sysMenu);

   /**
   *  新增表sys_menu信息
   *  @param sysMenu
   */
   Integer insertSysMenu(SysMenu sysMenu);
}
