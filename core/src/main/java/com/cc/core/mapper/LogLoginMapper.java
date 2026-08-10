package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.LogLogin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 登录日志 Mapper
 */
@Mapper
public interface LogLoginMapper extends BaseMapper<LogLogin> {

    /**
     * 分页查询登录日志
     */
    List<LogLogin> selectLogLoginPage(@Param("userName") String userName, @Param("status") Integer status);

    /**
     * 清空登录日志
     */
    @Delete("DELETE FROM log_login")
    int cleanAll();
}
