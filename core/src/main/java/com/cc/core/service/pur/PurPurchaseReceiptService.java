package com.cc.core.service.pur;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.pur.PurchaseReceiptQueryDTO;
import com.cc.core.entity.pur.PurPurchaseReceipt;
import com.cc.core.entity.pur.PurPurchaseReceiptItem;

import java.util.List;

/**
 * 采购入库单服务接口
 */
public interface PurPurchaseReceiptService extends IService<PurPurchaseReceipt> {

    /**
     * 分页查询采购入库单
     */
    IPage<PurPurchaseReceipt> page(PurchaseReceiptQueryDTO query);

    /**
     * 获取入库单明细列表
     */
    List<PurPurchaseReceiptItem> getItems(Long receiptId);

    /**
     * 审核入库单：更新库存、生成库存流水、生成应付账款
     */
    void approve(Long id);
}
