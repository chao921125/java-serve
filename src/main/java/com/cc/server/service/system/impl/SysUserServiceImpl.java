package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysUser;
import com.cc.server.mapper.system.SysUserMapper;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysUserService;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	@Resource
	private SysUserMapper sysUserMapper;

	/**
	 * 查询表sys_user所有信息
	 */
	@Override
	public List<SysUser> selectAllSysUser() {
		return sysUserMapper.selectAllSysUser();
	}

	/**
	 * 根据主键id查询表sys_user信息
	 *
	 * @param id
	 */
	@Override
	public SysUser selectSysUserById(@Param("id") Long id) {
		return sysUserMapper.selectSysUserById(id);
	}

	/**
	 * 根据条件查询表sys_user信息
	 *
	 * @param sysUser
	 */
	@Override
	public List<SysUser> selectSysUserByCondition(SysUser sysUser) {
		return sysUserMapper.selectSysUserByCondition(sysUser);
	}

	/**
	 * 根据主键id查询表sys_user信息
	 *
	 * @param id
	 */
	@Override
	public Integer deleteSysUserById(@Param("id") Long id) {
		return sysUserMapper.deleteSysUserById(id);
	}

	/**
	 * 根据主键id更新表sys_user信息
	 *
	 * @param sysUser
	 */
	@Override
	public Integer updateSysUserById(SysUser sysUser) {
		return sysUserMapper.updateSysUserById(sysUser);
	}

	/**
	 * 新增表sys_user信息
	 *
	 * @param sysUser
	 */
	@Override
	public Integer insertSysUser(SysUser sysUser) {
//		sysUser.setPassword(PasswordEncoder.encode(sysUser.getPassword()));
		return sysUserMapper.insertSysUser(sysUser);
	}

	/**
	 * 新增表sys_user信息
	 *
	 * @param sysUser
	 */
	@Override
	public SysUser getUserByNameEmailPhone(SysUser sysUser) {
		return sysUserMapper.loginSysUser(sysUser);
	}
}
