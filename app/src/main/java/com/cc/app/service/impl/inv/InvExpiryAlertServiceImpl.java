package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.inv.InvExpiryAlert;
import com.cc.core.mapper.inv.InvExpiryAlertMapper;
import com.cc.core.service.inv.InvExpiryAlertService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.core.entity.inv.InvInventory;

/**
 * InvExpiryAlert 服务实现
 */
@Service
@RequiredArgsConstructor
public class InvExpiryAlertServiceImpl extends ServiceImpl<InvExpiryAlertMapper, InvExpiryAlert> implements InvExpiryAlertService {
    private final InvInventoryService inventoryService;


    // ==== Business Logic Methods ====

    @Override
    public int scanAndAlert() {
        int count = 0;
        java.time.LocalDate now = java.time.LocalDate.now();
        java.util.List<InvInventory> inventories = inventoryService.list();
        for (InvInventory inv : inventories) {
            if (inv.getBatchNo() == null || inv.getBatchNo().isEmpty()) continue;
            // 从商品表读取保质期天数（此处简化，实际应从商品档案读取）
            // 假设商品有 expiry_date 需要从批次信息获取
        }
        // 查询所有有保质期的库存记录
        java.util.List<InvInventory> batchInventories = inventoryService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<InvInventory>()
                .isNotNull(InvInventory::getBatchNo)
                .ne(InvInventory::getBatchNo, "")
        );
        for (InvInventory inv : batchInventories) {
            // 此处简化：实际需要从批次表或商品表获取到期日期
            // 留作扩展，仅演示结构
        }
        return count;
    }

    @Override
    public void handle(Long id, String handleMethod) {
        InvExpiryAlert alert = getById(id);
        alert.setHandled(1);
        alert.setHandledTime(java.time.LocalDateTime.now().toString());
        alert.setHandleMethod(handleMethod);
        updateById(alert);
    }

    @Override
    public java.util.Map<String, Object> getStats() {
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("expired", lambdaQuery().eq(InvExpiryAlert::getAlertLevel, "EXPIRED").eq(InvExpiryAlert::getHandled, 0).count());
        stats.put("urgent", lambdaQuery().eq(InvExpiryAlert::getAlertLevel, "URGENT").eq(InvExpiryAlert::getHandled, 0).count());
        stats.put("warning", lambdaQuery().eq(InvExpiryAlert::getAlertLevel, "WARNING").eq(InvExpiryAlert::getHandled, 0).count());
        stats.put("notice", lambdaQuery().eq(InvExpiryAlert::getAlertLevel, "NOTICE").eq(InvExpiryAlert::getHandled, 0).count());
        stats.put("total", lambdaQuery().eq(InvExpiryAlert::getHandled, 0).count());
        return stats;
    }

}
