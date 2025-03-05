package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysUserPost;
import com.cc.server.mapper.system.SysUserPostMapper;
import org.apache.ibatis.annotations.Param;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysUserPostService;

import java.util.List;

/**
 * <p>
 * 用户岗位 用户1-N岗位 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostService {
	@Resource
	private SysUserPostMapper sysUserPostMapper;

	/**
	 * 查询表sys_user_post所有信息
	 */
	@Override
	public List<SysUserPost> selectAllSysUserPost() {
		return sysUserPostMapper.selectAllSysUserPost();
	}

	/**
	 * 根据主键id查询表sys_user_post信息
	 *
	 * @param id
	 */
	@Override
	public SysUserPost selectSysUserPostById(@Param("id") Long id) {
		return sysUserPostMapper.selectSysUserPostById(id);
	}

	/**
	 * 根据条件查询表sys_user_post信息
	 *
	 * @param sysUserPost
	 */
	@Override
	public List<SysUserPost> selectSysUserPostByCondition(SysUserPost sysUserPost) {
		return sysUserPostMapper.selectSysUserPostByCondition(sysUserPost);
	}

	/**
	 * 根据主键id查询表sys_user_post信息
	 *
	 * @param id
	 */
	@Override
	public Integer deleteSysUserPostById(@Param("id") Long id) {
		return sysUserPostMapper.deleteSysUserPostById(id);
	}

	/**
	 * 根据主键id更新表sys_user_post信息
	 *
	 * @param sysUserPost
	 */
	@Override
	public Integer updateSysUserPostById(SysUserPost sysUserPost) {
		return sysUserPostMapper.updateSysUserPostById(sysUserPost);
	}

	/**
	 * 新增表sys_user_post信息
	 *
	 * @param sysUserPost
	 */
	@Override
	public Integer insertSysUserPost(SysUserPost sysUserPost) {
		return sysUserPostMapper.insertSysUserPost(sysUserPost);
	}
}
