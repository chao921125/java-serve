package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.inv.InvStockTransferItem;
import com.cc.core.mapper.inv.InvStockTransferItemMapper;
import com.cc.core.service.inv.InvStockTransferItemService;
import org.springframework.stereotype.Service;

/**
 * 调拨单明细服务实现
 */
@Service
public class InvStockTransferItemServiceImpl
        extends ServiceImpl<InvStockTransferItemMapper, InvStockTransferItem>
        implements InvStockTransferItemService {
}
