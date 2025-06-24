package com.cc.server.service.system.impl;

import com.cc.server.entity.system.SysConfig;
import com.cc.server.mapper.system.SysConfigMapper;
import com.cc.server.service.system.SysConfigService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

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
} 