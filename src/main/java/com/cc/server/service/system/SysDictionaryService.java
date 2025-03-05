package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysDictionary;
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
public interface SysDictionaryService extends IService<SysDictionary> {
	/**
	 * 查询表sys_dictionary所有信息
	 */
	List<SysDictionary> selectAllSysDictionary();

	/**
	 * 根据主键id查询表sys_dictionary信息
	 *
	 * @param id
	 */
	SysDictionary selectSysDictionaryById(@Param("id") Integer id);

	/**
	 * 根据条件查询表sys_dictionary信息
	 *
	 * @param sysDictionary
	 */
	List<SysDictionary> selectSysDictionaryByCondition(SysDictionary sysDictionary);

	/**
	 * 根据主键id查询表sys_dictionary信息
	 *
	 * @param id
	 */
	Integer deleteSysDictionaryById(@Param("id") Integer id);

	/**
	 * 根据主键id更新表sys_dictionary信息
	 *
	 * @param sysDictionary
	 */
	Integer updateSysDictionaryById(SysDictionary sysDictionary);

	/**
	 * 新增表sys_dictionary信息
	 *
	 * @param sysDictionary
	 */
	Integer insertSysDictionary(SysDictionary sysDictionary);
}
