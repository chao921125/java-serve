package com.cc.net.mapper.log;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.net.entity.log.LogLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2023-42-08 14:10:202
 */
@Component
@Mapper
public interface LogLoginMapper extends BaseMapper<LogLogin> {
    ArrayList<LogLogin> selectListByTime(String startDate, String endDate);
}
