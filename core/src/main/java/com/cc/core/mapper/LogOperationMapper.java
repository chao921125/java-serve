package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.LogOperation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志 Mapper
 */
@Mapper
public interface LogOperationMapper extends BaseMapper<LogOperation> {

    /**
     * 分页查询操作日志
     */
    List<LogOperation> selectLogOperationPage(@Param("title") String title,
                                               @Param("userName") String userName,
                                               @Param("status") Integer status);

    /**
     * 清空操作日志
     */
    @Delete("DELETE FROM log_operation")
    int cleanAll();
}
