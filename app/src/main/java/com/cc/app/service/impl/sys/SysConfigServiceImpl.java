package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysConfig;
import com.cc.core.mapper.sys.SysConfigMapper;
import com.cc.core.service.sys.SysConfigService;
import org.springframework.stereotype.Service;

/**
 * SysConfig 服务实现
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
}
