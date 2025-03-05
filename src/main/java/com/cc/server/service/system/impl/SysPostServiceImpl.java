package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysPost;
import com.cc.server.mapper.system.SysPostMapper;
import org.apache.ibatis.annotations.Param;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysPostService;

import java.util.List;

/**
 * <p>
 * 岗位表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {
	@Resource
	private SysPostMapper sysPostMapper;

	/**
	 * 查询表sys_post所有信息
	 */
	@Override
	public List<SysPost> selectAllSysPost() {
		return sysPostMapper.selectAllSysPost();
	}

	/**
	 * 根据主键id查询表sys_post信息
	 *
	 * @param id
	 */
	@Override
	public SysPost selectSysPostById(@Param("id") Long id) {
		return sysPostMapper.selectSysPostById(id);
	}

	/**
	 * 根据条件查询表sys_post信息
	 *
	 * @param sysPost
	 */
	@Override
	public List<SysPost> selectSysPostByCondition(SysPost sysPost) {
		return sysPostMapper.selectSysPostByCondition(sysPost);
	}

	/**
	 * 根据主键id查询表sys_post信息
	 *
	 * @param id
	 */
	@Override
	public Integer deleteSysPostById(@Param("id") Long id) {
		return sysPostMapper.deleteSysPostById(id);
	}

	/**
	 * 根据主键id更新表sys_post信息
	 *
	 * @param sysPost
	 */
	@Override
	public Integer updateSysPostById(SysPost sysPost) {
		return sysPostMapper.updateSysPostById(sysPost);
	}

	/**
	 * 新增表sys_post信息
	 *
	 * @param sysPost
	 */
	@Override
	public Integer insertSysPost(SysPost sysPost) {
		return sysPostMapper.insertSysPost(sysPost);
	}
}
