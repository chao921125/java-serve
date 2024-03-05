package com.cc.serve.mapper.log;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.serve.entity.log.LogLogin;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2023-42-08 14:10:202
 */
@Mapper
public interface LogLoginMapper extends BaseMapper<LogLogin> {
    ArrayList<LogLogin> selectListByTime(String startDate, String endDate);
}
