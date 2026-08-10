package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurPurchaseOrderItem;
import com.cc.core.mapper.pur.PurPurchaseOrderItemMapper;
import com.cc.core.service.pur.PurPurchaseOrderItemService;
import org.springframework.stereotype.Service;

/**
 * 采购订单明细服务实现
 */
@Service
public class PurPurchaseOrderItemServiceImpl extends ServiceImpl<PurPurchaseOrderItemMapper, PurPurchaseOrderItem>
        implements PurPurchaseOrderItemService {
}
