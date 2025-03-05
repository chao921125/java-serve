package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysDepartmentService extends IService<SysDepartment> {
	/**
	 * 查询表sys_department所有信息
	 */
	List<SysDepartment> selectAllSysDepartment();

	/**
	 * 根据主键id查询表sys_department信息
	 *
	 * @param id
	 */
	SysDepartment selectSysDepartmentById(@Param("id") Long id);

	/**
	 * 根据条件查询表sys_department信息
	 *
	 * @param sysDepartment
	 */
	List<SysDepartment> selectSysDepartmentByCondition(SysDepartment sysDepartment);

	/**
	 * 根据主键id查询表sys_department信息
	 *
	 * @param id
	 */
	Integer deleteSysDepartmentById(@Param("id") Long id);

	/**
	 * 根据主键id更新表sys_department信息
	 *
	 * @param sysDepartment
	 */
	Integer updateSysDepartmentById(SysDepartment sysDepartment);

	/**
	 * 新增表sys_department信息
	 *
	 * @param sysDepartment
	 */
	Integer insertSysDepartment(SysDepartment sysDepartment);
}
