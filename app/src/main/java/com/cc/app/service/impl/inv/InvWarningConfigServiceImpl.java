package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.inv.InvWarningConfig;
import com.cc.core.mapper.inv.InvWarningConfigMapper;
import com.cc.core.service.inv.InvWarningConfigService;
import org.springframework.stereotype.Service;

/**
 * 库存预警配置服务实现
 */
@Service
public class InvWarningConfigServiceImpl extends ServiceImpl<InvWarningConfigMapper, InvWarningConfig>
        implements InvWarningConfigService {
}
