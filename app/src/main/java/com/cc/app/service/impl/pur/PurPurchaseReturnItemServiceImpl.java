package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurPurchaseReturnItem;
import com.cc.core.mapper.pur.PurPurchaseReturnItemMapper;
import com.cc.core.service.pur.PurPurchaseReturnItemService;
import org.springframework.stereotype.Service;

/**
 * 采购退货单明细服务实现
 */
@Service
public class PurPurchaseReturnItemServiceImpl extends ServiceImpl<PurPurchaseReturnItemMapper, PurPurchaseReturnItem>
        implements PurPurchaseReturnItemService {
}
