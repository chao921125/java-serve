package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysUserRole;
import com.cc.server.mapper.system.SysUserRoleMapper;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysUserRoleService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 用户角色 用户N-1角色 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;

	/**
	 * 查询表sys_user_role所有信息
	 */
	@Override
	public List<SysUserRole> selectAllSysUserRole() {
		return sysUserRoleMapper.selectAllSysUserRole();
	}

	/**
	 * 根据主键id查询表sys_user_role信息
	 *
	 * @param id
	 */
	@Override
	public SysUserRole selectSysUserRoleById(@Param("id") Long id) {
		return sysUserRoleMapper.selectSysUserRoleById(id);
	}

	/**
	 * 根据条件查询表sys_user_role信息
	 *
	 * @param sysUserRole
	 */
	@Override
	public List<SysUserRole> selectSysUserRoleByCondition(SysUserRole sysUserRole) {
		return sysUserRoleMapper.selectSysUserRoleByCondition(sysUserRole);
	}

	/**
	 * 根据主键id查询表sys_user_role信息
	 *
	 * @param id
	 */
	@Override
	public Integer deleteSysUserRoleById(@Param("id") Long id) {
		return sysUserRoleMapper.deleteSysUserRoleById(id);
	}

	/**
	 * 根据主键id更新表sys_user_role信息
	 *
	 * @param sysUserRole
	 */
	@Override
	public Integer updateSysUserRoleById(SysUserRole sysUserRole) {
		return sysUserRoleMapper.updateSysUserRoleById(sysUserRole);
	}

	/**
	 * 新增表sys_user_role信息
	 *
	 * @param sysUserRole
	 */
	@Override
	public Integer insertSysUserRole(SysUserRole sysUserRole) {
		return sysUserRoleMapper.insertSysUserRole(sysUserRole);
	}

	@Override
	public PageResult<SysUserRole> pageSysUserRole(PageRequest pageRequest) {
		Page<SysUserRole> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
		Page<SysUserRole> result = this.page(page, new QueryWrapper<>());
		return new PageResult<>(result.getTotal(), result.getRecords());
	}
}
