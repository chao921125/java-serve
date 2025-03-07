package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 岗位表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysPostService extends IService<SysPost> {
	/**
	 * 查询表sys_post所有信息
	 */
	List<SysPost> selectAllSysPost();

	/**
	 * 根据主键id查询表sys_post信息
	 *
	 * @param id
	 */
	SysPost selectSysPostById(@Param("id") Long id);

	/**
	 * 根据条件查询表sys_post信息
	 *
	 * @param sysPost
	 */
	List<SysPost> selectSysPostByCondition(SysPost sysPost);

	/**
	 * 根据主键id查询表sys_post信息
	 *
	 * @param id
	 */
	Integer deleteSysPostById(@Param("id") Long id);

	/**
	 * 根据主键id更新表sys_post信息
	 *
	 * @param sysPost
	 */
	Integer updateSysPostById(SysPost sysPost);

	/**
	 * 新增表sys_post信息
	 *
	 * @param sysPost
	 */
	Integer insertSysPost(SysPost sysPost);
}
