package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.inv.InvStockTakeItem;
import com.cc.core.mapper.inv.InvStockTakeItemMapper;
import com.cc.core.service.inv.InvStockTakeItemService;
import org.springframework.stereotype.Service;

/**
 * 盘点单明细服务实现
 */
@Service
public class InvStockTakeItemServiceImpl extends ServiceImpl<InvStockTakeItemMapper, InvStockTakeItem>
        implements InvStockTakeItemService {
}
