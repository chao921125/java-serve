package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysDepartment;
import com.cc.server.mapper.system.SysDepartmentMapper;
import org.apache.ibatis.annotations.Param;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysDepartmentService;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {
	@Resource
	private SysDepartmentMapper sysDepartmentMapper;

	/**
	 * 查询表sys_department所有信息
	 */
	@Override
	public List<SysDepartment> selectAllSysDepartment() {
		return sysDepartmentMapper.selectAllSysDepartment();
	}

	/**
	 * 根据主键id查询表sys_department信息
	 *
	 * @param id
	 */
	@Override
	public SysDepartment selectSysDepartmentById(@Param("id") Long id) {
		return sysDepartmentMapper.selectSysDepartmentById(id);
	}

	/**
	 * 根据条件查询表sys_department信息
	 *
	 * @param sysDepartment
	 */
	@Override
	public List<SysDepartment> selectSysDepartmentByCondition(SysDepartment sysDepartment) {
		return sysDepartmentMapper.selectSysDepartmentByCondition(sysDepartment);
	}

	/**
	 * 根据主键id查询表sys_department信息
	 *
	 * @param id
	 */
	@Override
	public Integer deleteSysDepartmentById(@Param("id") Long id) {
		return sysDepartmentMapper.deleteSysDepartmentById(id);
	}

	/**
	 * 根据主键id更新表sys_department信息
	 *
	 * @param sysDepartment
	 */
	@Override
	public Integer updateSysDepartmentById(SysDepartment sysDepartment) {
		return sysDepartmentMapper.updateSysDepartmentById(sysDepartment);
	}

	/**
	 * 新增表sys_department信息
	 *
	 * @param sysDepartment
	 */
	@Override
	public Integer insertSysDepartment(SysDepartment sysDepartment) {
		return sysDepartmentMapper.insertSysDepartment(sysDepartment);
	}
}
