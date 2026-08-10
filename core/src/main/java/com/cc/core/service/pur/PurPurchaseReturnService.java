package com.cc.core.service.pur;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.pur.PurchaseReturnQueryDTO;
import com.cc.core.entity.pur.PurPurchaseReturn;
import com.cc.core.entity.pur.PurPurchaseReturnItem;

import java.util.List;

/**
 * 采购退货单服务接口
 */
public interface PurPurchaseReturnService extends IService<PurPurchaseReturn> {

    /**
     * 分页查询采购退货单
     */
    IPage<PurPurchaseReturn> page(PurchaseReturnQueryDTO query);

    /**
     * 获取退货单明细列表
     */
    List<PurPurchaseReturnItem> getItems(Long returnId);

    /**
     * 审核退货单
     */
    void approve(Long id);
}
