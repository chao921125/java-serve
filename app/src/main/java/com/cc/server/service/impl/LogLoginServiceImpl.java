package com.cc.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.LogLogin;
import com.cc.core.mapper.LogLoginMapper;
import com.cc.core.service.LogLoginService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录日志服务实现
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements LogLoginService {

    /**
     * 分页查询登录日志
     */
    public Page<LogLogin> selectPage(Integer pageNum, Integer pageSize, String userName, Integer status) {
        Page<LogLogin> page = new Page<>(pageNum, pageSize);
        List<LogLogin> list = baseMapper.selectLogLoginPage(userName, status);
        page.setRecords(list);
        page.setTotal(list.size());
        return page;
    }

    @Override
    public void cleanAll() {
        baseMapper.cleanAll();
    }
}
