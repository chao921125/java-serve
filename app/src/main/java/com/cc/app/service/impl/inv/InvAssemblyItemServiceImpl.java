package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.inv.InvAssemblyItem;
import com.cc.core.mapper.inv.InvAssemblyItemMapper;
import com.cc.core.service.inv.InvAssemblyItemService;
import org.springframework.stereotype.Service;

/**
 * 组装拆卸单明细服务实现
 */
@Service
public class InvAssemblyItemServiceImpl extends ServiceImpl<InvAssemblyItemMapper, InvAssemblyItem>
        implements InvAssemblyItemService {
}
