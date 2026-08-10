package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.inv.InvStockAging;
import com.cc.core.mapper.inv.InvStockAgingMapper;
import com.cc.core.service.inv.InvStockAgingService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.core.entity.inv.InvInventory;

/**
 * InvStockAging 服务实现
 */
@Service
@RequiredArgsConstructor
public class InvStockAgingServiceImpl extends ServiceImpl<InvStockAgingMapper, InvStockAging> implements InvStockAgingService {
    private final InvInventoryService inventoryService;


    // ==== Business Logic Methods ====

    @Override
    public void generateSnapshot() {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.util.List<InvInventory> inventories = inventoryService.list();
        for (InvInventory inv : inventories) {
            InvStockAging aging = new InvStockAging();
            aging.setSnapshotDate(today);
            aging.setProductId(inv.getProductId());
            aging.setWarehouseId(inv.getWarehouseId());
            aging.setBatchNo(inv.getBatchNo());
            aging.setQuantity(inv.getQuantity());
            aging.setCostAmount(inv.getTotalCost());
            java.time.LocalDate lastDate = inv.getUpdateTime() != null ? inv.getUpdateTime().toLocalDate() : today;
            aging.setLastInboundDate(lastDate);
            int agingDays = (int) java.time.temporal.ChronoUnit.DAYS.between(lastDate, today);
            aging.setAgingDays(agingDays);
            aging.setAgingBucket(getAgingBucket(agingDays));
            aging.setIsSlowMoving(agingDays > 180 ? 1 : 0);
            save(aging);
        }
    }

    private String getAgingBucket(int days) {
        if (days <= 30) return "0-30";
        if (days <= 60) return "31-60";
        if (days <= 90) return "61-90";
        if (days <= 180) return "91-180";
        if (days <= 365) return "181-365";
        return "365+";
    }

    @Override
    public com.baomidou.mybatisplus.extension.plugins.pagination.Page<InvStockAging> getSlowMoving(com.baomidou.mybatisplus.extension.plugins.pagination.Page<InvStockAging> page) {
        return lambdaQuery().eq(InvStockAging::getIsSlowMoving, 1)
                .orderByDesc(InvStockAging::getAgingDays)
                .page(page);
    }

    @Override
    public java.util.List<java.util.Map<String, Object>> getTurnoverRate(Integer days) {
        java.util.List<java.util.Map<String, Object>> result = new java.util.ArrayList<>();
        var agings = lambdaQuery().ge(InvStockAging::getSnapshotDate,
                java.time.LocalDate.now().minusDays(days)).list();
        for (var aging : agings) {
            java.util.Map<String, Object> item = new java.util.HashMap<>();
            item.put("productId", aging.getProductId());
            item.put("quantity", aging.getQuantity());
            item.put("agingDays", aging.getAgingDays());
            item.put("turnoverRate", aging.getTurnoverRate());
            result.add(item);
        }
        return result;
    }

}
