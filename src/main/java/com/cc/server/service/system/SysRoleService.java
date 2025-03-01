package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysRoleService extends IService<SysRole> {
  /**
  *  查询表sys_role所有信息
  */
  List<SysRole> selectAllSysRole();

   /**
   *  根据主键id查询表sys_role信息
   *  @param id
   */
   SysRole selectSysRoleById(@Param("id") Long id);

  /**
  *  根据条件查询表sys_role信息
  *  @param sysRole
  */
  List<SysRole> selectSysRoleByCondition(SysRole sysRole);

   /**
   *  根据主键id查询表sys_role信息
   *  @param id
   */
   Integer deleteSysRoleById(@Param("id") Long id);

   /**
   *  根据主键id更新表sys_role信息
   *  @param sysRole
   */
   Integer updateSysRoleById(SysRole sysRole);

   /**
   *  新增表sys_role信息
   *  @param sysRole
   */
   Integer insertSysRole(SysRole sysRole);
}
