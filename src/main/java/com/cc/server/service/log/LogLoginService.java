package com.cc.server.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.log.LogLogin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface LogLoginService extends IService<LogLogin> {
	/**
	 * 查询表log_login所有信息
	 */
	List<LogLogin> selectAllLogLogin();

	/**
	 * 根据主键id查询表log_login信息
	 *
	 * @param id
	 */
	LogLogin selectLogLoginById(@Param("id") Long id);

	/**
	 * 根据条件查询表log_login信息
	 *
	 * @param logLogin
	 */
	List<LogLogin> selectLogLoginByCondition(LogLogin logLogin);

	/**
	 * 根据主键id查询表log_login信息
	 *
	 * @param id
	 */
	Integer deleteLogLoginById(@Param("id") Long id);

	/**
	 * 根据主键id更新表log_login信息
	 *
	 * @param logLogin
	 */
	Integer updateLogLoginById(LogLogin logLogin);

	/**
	 * 新增表log_login信息
	 *
	 * @param logLogin
	 */
	Integer insertLogLogin(LogLogin logLogin);
}
