package com.cc.serve.service.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.serve.entity.log.LogLogin;
import com.cc.serve.mapper.log.LogLoginMapper;
import com.cc.serve.service.log.LogLoginService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cc
 * @since 2023-42-08 14:10:202
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements LogLoginService {

    @Resource
    LogLoginMapper logLoginMapper;

    public LogLogin getLogLoginById(String id) {
        return logLoginMapper.selectById(id);
    }
}
