package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurPurchaseReceiptItem;
import com.cc.core.mapper.pur.PurPurchaseReceiptItemMapper;
import com.cc.core.service.pur.PurPurchaseReceiptItemService;
import org.springframework.stereotype.Service;

/**
 * 采购入库单明细服务实现
 */
@Service
public class PurPurchaseReceiptItemServiceImpl extends ServiceImpl<PurPurchaseReceiptItemMapper, PurPurchaseReceiptItem>
        implements PurPurchaseReceiptItemService {
}
