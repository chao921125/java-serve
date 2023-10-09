package com.cc.net.service.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.net.entity.log.LogLogin;
import com.cc.net.mapper.log.LogLoginMapper;
import com.cc.net.service.log.LogLoginService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cc
 * @since 2023-42-08 14:10:202
 */
@Component
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements LogLoginService {

    @Resource
    LogLoginMapper logLoginMapper;

    public LogLogin getLogLoginById(String id) {
        return logLoginMapper.selectById(id);
    }
}
