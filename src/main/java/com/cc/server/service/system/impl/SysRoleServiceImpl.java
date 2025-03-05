package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysRole;
import com.cc.server.mapper.system.SysRoleMapper;
import org.apache.ibatis.annotations.Param;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysRoleService;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
	@Resource
	private SysRoleMapper sysRoleMapper;

	/**
	 * 查询表sys_role所有信息
	 */
	@Override
	public List<SysRole> selectAllSysRole() {
		return sysRoleMapper.selectAllSysRole();
	}

	/**
	 * 根据主键id查询表sys_role信息
	 *
	 * @param id
	 */
	@Override
	public SysRole selectSysRoleById(@Param("id") Long id) {
		return sysRoleMapper.selectSysRoleById(id);
	}

	/**
	 * 根据条件查询表sys_role信息
	 *
	 * @param sysRole
	 */
	@Override
	public List<SysRole> selectSysRoleByCondition(SysRole sysRole) {
		return sysRoleMapper.selectSysRoleByCondition(sysRole);
	}

	/**
	 * 根据主键id查询表sys_role信息
	 *
	 * @param id
	 */
	@Override
	public Integer deleteSysRoleById(@Param("id") Long id) {
		return sysRoleMapper.deleteSysRoleById(id);
	}

	/**
	 * 根据主键id更新表sys_role信息
	 *
	 * @param sysRole
	 */
	@Override
	public Integer updateSysRoleById(SysRole sysRole) {
		return sysRoleMapper.updateSysRoleById(sysRole);
	}

	/**
	 * 新增表sys_role信息
	 *
	 * @param sysRole
	 */
	@Override
	public Integer insertSysRole(SysRole sysRole) {
		return sysRoleMapper.insertSysRole(sysRole);
	}
}
