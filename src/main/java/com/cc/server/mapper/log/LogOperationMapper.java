package com.cc.server.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.server.entity.log.LogOperation;
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
public interface LogOperationMapper extends BaseMapper<LogOperation> {
  /**
  *  查询表log_operation所有信息
  */
  List<LogOperation> selectAllLogOperation();

  /**
  *  根据主键id查询表log_operation信息
  *  @param id
  */
  LogOperation selectLogOperationById(@Param("id") Long id);

  /**
  *  根据条件查询表log_operation信息
  *  @param logOperation
  */
  List<LogOperation> selectLogOperationByCondition(LogOperation logOperation);

  /**
  *  根据主键id查询表log_operation信息
  *  @param id
  */
  Integer deleteLogOperationById(@Param("id") Long id);

  /**
  *  根据主键id更新表log_operation信息
  *  @param logOperation
  */
  Integer updateLogOperationById(LogOperation logOperation);

  /**
  *  新增表log_operation信息
  *  @param logOperation
  */
  Integer insertLogOperation(LogOperation logOperation);

}
