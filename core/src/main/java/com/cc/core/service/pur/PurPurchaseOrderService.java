package com.cc.core.service.pur;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.pur.PurchaseOrderQueryDTO;
import com.cc.core.dto.pur.PurchaseOrderSaveDTO;
import com.cc.core.entity.pur.PurPurchaseOrder;
import com.cc.core.entity.pur.PurPurchaseOrderItem;

import java.util.List;

/**
 * 采购订单服务接口
 */
public interface PurPurchaseOrderService extends IService<PurPurchaseOrder> {

    /**
     * 分页查询采购订单
     */
    IPage<PurPurchaseOrder> page(PurchaseOrderQueryDTO query);

    /**
     * 创建采购订单（含明细）
     */
    void create(PurchaseOrderSaveDTO dto);

    /**
     * 更新采购订单（含明细，先删旧明细再插新明细）
     */
    void update(Long id, PurchaseOrderSaveDTO dto);

    /**
     * 审核采购订单
     */
    void approve(Long id);

    /**
     * 反审核采购订单
     */
    void reject(Long id);

    /**
     * 关闭采购订单
     */
    void close(Long id);

    /**
     * 获取采购订单明细列表
     */
    List<PurPurchaseOrderItem> getItems(Long orderId);
}
