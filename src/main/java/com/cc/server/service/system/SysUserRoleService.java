package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色 用户N-1角色 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysUserRoleService extends IService<SysUserRole> {
	/**
	 * 查询表sys_user_role所有信息
	 */
	List<SysUserRole> selectAllSysUserRole();

	/**
	 * 根据主键id查询表sys_user_role信息
	 *
	 * @param id
	 */
	SysUserRole selectSysUserRoleById(@Param("id") Long id);

	/**
	 * 根据条件查询表sys_user_role信息
	 *
	 * @param sysUserRole
	 */
	List<SysUserRole> selectSysUserRoleByCondition(SysUserRole sysUserRole);

	/**
	 * 根据主键id查询表sys_user_role信息
	 *
	 * @param id
	 */
	Integer deleteSysUserRoleById(@Param("id") Long id);

	/**
	 * 根据主键id更新表sys_user_role信息
	 *
	 * @param sysUserRole
	 */
	Integer updateSysUserRoleById(SysUserRole sysUserRole);

	/**
	 * 新增表sys_user_role信息
	 *
	 * @param sysUserRole
	 */
	Integer insertSysUserRole(SysUserRole sysUserRole);
}
