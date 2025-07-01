package com.cc.server.service.system;

import com.cc.server.entity.system.SysConfig;
import java.util.List;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;

public interface SysConfigService {
    List<SysConfig> selectAllSysConfig();
    SysConfig selectSysConfigById(Long id);
    SysConfig selectSysConfigByKey(String configKey);
    Integer deleteSysConfigById(Long id);
    Integer updateSysConfigById(SysConfig sysConfig);
    Integer insertSysConfig(SysConfig sysConfig);
    /**
     * 分页查询配置
     */
    PageResult<SysConfig> pageSysConfig(PageRequest pageRequest);
} 