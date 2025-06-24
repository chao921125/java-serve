package com.cc.server.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.server.entity.system.SysConfig;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SysConfigMapper extends BaseMapper<SysConfig> {
    List<SysConfig> selectAllSysConfig();
    SysConfig selectSysConfigById(@Param("id") Long id);
    SysConfig selectSysConfigByKey(@Param("configKey") String configKey);
    Integer deleteSysConfigById(@Param("id") Long id);
    Integer updateSysConfigById(SysConfig sysConfig);
    Integer insertSysConfig(SysConfig sysConfig);
} 