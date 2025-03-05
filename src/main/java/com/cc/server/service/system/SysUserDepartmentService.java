package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysUserDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户部门 用户1-1 部门 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysUserDepartmentService extends IService<SysUserDepartment> {
	/**
	 * 查询表sys_user_department所有信息
	 */
	List<SysUserDepartment> selectAllSysUserDepartment();

	/**
	 * 根据主键id查询表sys_user_department信息
	 *
	 * @param id
	 */
	SysUserDepartment selectSysUserDepartmentById(@Param("id") Long id);

	/**
	 * 根据条件查询表sys_user_department信息
	 *
	 * @param sysUserDepartment
	 */
	List<SysUserDepartment> selectSysUserDepartmentByCondition(SysUserDepartment sysUserDepartment);

	/**
	 * 根据主键id查询表sys_user_department信息
	 *
	 * @param id
	 */
	Integer deleteSysUserDepartmentById(@Param("id") Long id);

	/**
	 * 根据主键id更新表sys_user_department信息
	 *
	 * @param sysUserDepartment
	 */
	Integer updateSysUserDepartmentById(SysUserDepartment sysUserDepartment);

	/**
	 * 新增表sys_user_department信息
	 *
	 * @param sysUserDepartment
	 */
	Integer insertSysUserDepartment(SysUserDepartment sysUserDepartment);
}
