package com.cc.server.service.system.impl;

import com.cc.server.entity.system.SysConfig;
import com.cc.server.mapper.system.SysConfigMapper;
import com.cc.server.service.system.SysConfigService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class SysConfigServiceImpl implements SysConfigService {
    @Resource
    private SysConfigMapper sysConfigMapper;

    @Override
    public List<SysConfig> selectAllSysConfig() {
        return sysConfigMapper.selectAllSysConfig();
    }

    @Override
    public SysConfig selectSysConfigById(Long id) {
        return sysConfigMapper.selectSysConfigById(id);
    }

    @Override
    public SysConfig selectSysConfigByKey(String configKey) {
        return sysConfigMapper.selectSysConfigByKey(configKey);
    }

    @Override
    public Integer deleteSysConfigById(Long id) {
        return sysConfigMapper.deleteSysConfigById(id);
    }

    @Override
    public Integer updateSysConfigById(SysConfig sysConfig) {
        return sysConfigMapper.updateSysConfigById(sysConfig);
    }

    @Override
    public Integer insertSysConfig(SysConfig sysConfig) {
        return sysConfigMapper.insertSysConfig(sysConfig);
    }

    @Override
    public PageResult<SysConfig> pageSysConfig(PageRequest pageRequest) {
        Page<SysConfig> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        Page<SysConfig> result = sysConfigMapper.selectPage(page, new QueryWrapper<>());
        return new PageResult<>(result.getTotal(), result.getRecords());
    }
} 