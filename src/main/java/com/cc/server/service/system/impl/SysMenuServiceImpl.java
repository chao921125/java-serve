package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysMenu;
import com.cc.server.mapper.system.SysMenuMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysMenuService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
  @Autowired
  private SysMenuMapper sysMenuMapper;

 /**
 *  查询表sys_menu所有信息
 */
 @Override
 public List<SysMenu> selectAllSysMenu() { return sysMenuMapper.selectAllSysMenu();}

   /**
   *  根据主键id查询表sys_menu信息
   *  @param id
   */
   @Override
   public SysMenu selectSysMenuById(@Param("id") Long id) { return sysMenuMapper.selectSysMenuById(id);}

 /**
 *  根据条件查询表sys_menu信息
 *  @param sysMenu
 */
 @Override
 public List<SysMenu> selectSysMenuByCondition(SysMenu sysMenu) { return sysMenuMapper.selectSysMenuByCondition(sysMenu);}

   /**
   *  根据主键id查询表sys_menu信息
   *  @param id
   */
   @Override
   public Integer deleteSysMenuById(@Param("id") Long id) { return sysMenuMapper.deleteSysMenuById(id);}

   /**
   *  根据主键id更新表sys_menu信息
   *  @param sysMenu
   */
   @Override
   public Integer updateSysMenuById(SysMenu sysMenu) { return sysMenuMapper.updateSysMenuById(sysMenu);}

   /**
   *  新增表sys_menu信息
   *  @param sysMenu
   */
   @Override
   public Integer insertSysMenu(SysMenu sysMenu) { return sysMenuMapper.insertSysMenu(sysMenu);}
}
