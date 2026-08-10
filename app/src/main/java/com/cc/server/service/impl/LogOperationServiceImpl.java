package com.cc.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.LogOperation;
import com.cc.core.mapper.LogOperationMapper;
import com.cc.core.service.LogOperationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志服务实现
 */
@Service
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperation> implements LogOperationService {

    /**
     * 分页查询操作日志
     */
    public Page<LogOperation> selectPage(Integer pageNum, Integer pageSize, String title, String userName, Integer status) {
        Page<LogOperation> page = new Page<>(pageNum, pageSize);
        List<LogOperation> list = baseMapper.selectLogOperationPage(title, userName, status);
        page.setRecords(list);
        page.setTotal(list.size());
        return page;
    }

    @Override
    public void cleanAll() {
        baseMapper.cleanAll();
    }
}
