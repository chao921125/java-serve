package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sal.SalExchangeOrder;
import com.cc.core.mapper.sal.SalExchangeOrderMapper;
import com.cc.core.service.sal.SalExchangeOrderService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.cc.core.service.sal.SalExchangeReturnItemService;
import com.cc.core.service.sal.SalExchangeOutItemService;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.core.entity.sal.SalExchangeReturnItem;
import com.cc.core.entity.sal.SalExchangeOutItem;

/**
 * SalExchangeOrder 服务实现
 */
@Service
@RequiredArgsConstructor
public class SalExchangeOrderServiceImpl extends ServiceImpl<SalExchangeOrderMapper, SalExchangeOrder> implements SalExchangeOrderService {
    private final SalExchangeReturnItemService exchangeReturnItemService;
    private final SalExchangeOutItemService exchangeOutItemService;
    private final InvInventoryService invInventoryService;


    // ==== Business Logic Methods ====

    @Override
    public void approve(Long id, Long approverId) {
        com.cc.core.entity.sal.SalExchangeOrder entity = getById(id);
        if (entity.getStatus() != 1) throw new RuntimeException("仅待审核状态可审批");
        entity.setStatus(2);
        entity.setApproverId(approverId);
        entity.setApproveTime(java.time.LocalDateTime.now().toString());
        updateById(entity);
    }

    @Override
    public void complete(Long id) {
        com.cc.core.entity.sal.SalExchangeOrder entity = getById(id);
        if (entity.getStatus() != 2) throw new RuntimeException("仅已审核状态可完成");

        // 退回商品入库（增加库存）
        java.util.List<com.cc.core.entity.sal.SalExchangeReturnItem> returnItems = exchangeReturnItemService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.cc.core.entity.sal.SalExchangeReturnItem>()
                .eq(com.cc.core.entity.sal.SalExchangeReturnItem::getExchangeId, id)
        );
        for (com.cc.core.entity.sal.SalExchangeReturnItem item : returnItems) {
            invInventoryService.increaseStock(
                item.getProductId(), entity.getWarehouseId(), item.getQuantity(), item.getBatchNo());
        }

        // 换出商品出库（减少库存）
        java.util.List<com.cc.core.entity.sal.SalExchangeOutItem> outItems = exchangeOutItemService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.cc.core.entity.sal.SalExchangeOutItem>()
                .eq(com.cc.core.entity.sal.SalExchangeOutItem::getExchangeId, id)
        );
        for (com.cc.core.entity.sal.SalExchangeOutItem item : outItems) {
            invInventoryService.decreaseStock(
                item.getProductId(), entity.getWarehouseId(), item.getQuantity(), item.getBatchNo());
        }

        entity.setStatus(3);
        updateById(entity);
    }

    @Override
    public void calculateDifference(Long id) {
        com.cc.core.entity.sal.SalExchangeOrder entity = getById(id);
        java.math.BigDecimal returnTotal = entity.getReturnTotal() != null ? entity.getReturnTotal() : java.math.BigDecimal.ZERO;
        java.math.BigDecimal exchangeTotal = entity.getExchangeTotal() != null ? entity.getExchangeTotal() : java.math.BigDecimal.ZERO;
        entity.setDifferenceAmount(exchangeTotal.subtract(returnTotal));
        updateById(entity);
    }

}
