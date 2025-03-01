package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysUserService extends IService<SysUser> {
  /**
  *  查询表sys_user所有信息
  */
  List<SysUser> selectAllSysUser();

   /**
   *  根据主键id查询表sys_user信息
   *  @param id
   */
   SysUser selectSysUserById(@Param("id") Long id);

  /**
  *  根据条件查询表sys_user信息
  *  @param sysUser
  */
  List<SysUser> selectSysUserByCondition(SysUser sysUser);

   /**
   *  根据主键id查询表sys_user信息
   *  @param id
   */
   Integer deleteSysUserById(@Param("id") Long id);

   /**
   *  根据主键id更新表sys_user信息
   *  @param sysUser
   */
   Integer updateSysUserById(SysUser sysUser);

   /**
   *  新增表sys_user信息
   *  @param sysUser
   */
   Integer insertSysUser(SysUser sysUser);

   SysUser getUserByNameEmailPhone(SysUser sysUser);
}
