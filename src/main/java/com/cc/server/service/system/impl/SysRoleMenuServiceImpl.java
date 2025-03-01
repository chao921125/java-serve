package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysRoleMenu;
import com.cc.server.mapper.system.SysRoleMenuMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysRoleMenuService;

import java.util.List;

/**
 * <p>
 * 角色菜单 角色1-N菜单 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
  @Autowired
  private SysRoleMenuMapper sysRoleMenuMapper;

 /**
 *  查询表sys_role_menu所有信息
 */
 @Override
 public List<SysRoleMenu> selectAllSysRoleMenu() { return sysRoleMenuMapper.selectAllSysRoleMenu();}

   /**
   *  根据主键id查询表sys_role_menu信息
   *  @param id
   */
   @Override
   public SysRoleMenu selectSysRoleMenuById(@Param("id") Long id) { return sysRoleMenuMapper.selectSysRoleMenuById(id);}

 /**
 *  根据条件查询表sys_role_menu信息
 *  @param sysRoleMenu
 */
 @Override
 public List<SysRoleMenu> selectSysRoleMenuByCondition(SysRoleMenu sysRoleMenu) { return sysRoleMenuMapper.selectSysRoleMenuByCondition(sysRoleMenu);}

   /**
   *  根据主键id查询表sys_role_menu信息
   *  @param id
   */
   @Override
   public Integer deleteSysRoleMenuById(@Param("id") Long id) { return sysRoleMenuMapper.deleteSysRoleMenuById(id);}

   /**
   *  根据主键id更新表sys_role_menu信息
   *  @param sysRoleMenu
   */
   @Override
   public Integer updateSysRoleMenuById(SysRoleMenu sysRoleMenu) { return sysRoleMenuMapper.updateSysRoleMenuById(sysRoleMenu);}

   /**
   *  新增表sys_role_menu信息
   *  @param sysRoleMenu
   */
   @Override
   public Integer insertSysRoleMenu(SysRoleMenu sysRoleMenu) { return sysRoleMenuMapper.insertSysRoleMenu(sysRoleMenu);}
}
