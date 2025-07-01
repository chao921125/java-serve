package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysUserPost;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户岗位 用户1-N岗位 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysUserPostService extends IService<SysUserPost> {
	/**
	 * 查询表sys_user_post所有信息
	 */
	List<SysUserPost> selectAllSysUserPost();

	/**
	 * 根据主键id查询表sys_user_post信息
	 *
	 * @param id
	 */
	SysUserPost selectSysUserPostById(@Param("id") Long id);

	/**
	 * 根据条件查询表sys_user_post信息
	 *
	 * @param sysUserPost
	 */
	List<SysUserPost> selectSysUserPostByCondition(SysUserPost sysUserPost);

	/**
	 * 根据主键id查询表sys_user_post信息
	 *
	 * @param id
	 */
	Integer deleteSysUserPostById(@Param("id") Long id);

	/**
	 * 根据主键id更新表sys_user_post信息
	 *
	 * @param sysUserPost
	 */
	Integer updateSysUserPostById(SysUserPost sysUserPost);

	/**
	 * 新增表sys_user_post信息
	 *
	 * @param sysUserPost
	 */
	Integer insertSysUserPost(SysUserPost sysUserPost);

	/**
	 * 分页查询用户岗位
	 */
	PageResult<SysUserPost> pageSysUserPost(PageRequest pageRequest);
}
