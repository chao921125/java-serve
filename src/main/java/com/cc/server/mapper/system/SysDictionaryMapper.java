package com.cc.server.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.server.entity.system.SysDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {
  /**
  *  查询表sys_dictionary所有信息
  */
  List<SysDictionary> selectAllSysDictionary();

  /**
  *  根据主键id查询表sys_dictionary信息
  *  @param id
  */
  SysDictionary selectSysDictionaryById(@Param("id") Integer id);

  /**
  *  根据条件查询表sys_dictionary信息
  *  @param sysDictionary
  */
  List<SysDictionary> selectSysDictionaryByCondition(SysDictionary sysDictionary);

  /**
  *  根据主键id查询表sys_dictionary信息
  *  @param id
  */
  Integer deleteSysDictionaryById(@Param("id") Integer id);

  /**
  *  根据主键id更新表sys_dictionary信息
  *  @param sysDictionary
  */
  Integer updateSysDictionaryById(SysDictionary sysDictionary);

  /**
  *  新增表sys_dictionary信息
  *  @param sysDictionary
  */
  Integer insertSysDictionary(SysDictionary sysDictionary);

}
