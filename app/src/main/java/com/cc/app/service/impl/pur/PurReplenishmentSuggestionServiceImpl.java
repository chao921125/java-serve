package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurReplenishmentSuggestion;
import com.cc.core.mapper.pur.PurReplenishmentSuggestionMapper;
import com.cc.core.service.pur.PurReplenishmentSuggestionService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.core.service.inv.InvWarningConfigService;
import com.cc.core.service.pur.PurPurchaseRequisitionService;
import com.cc.core.entity.pur.PurPurchaseRequisition;
import com.cc.core.entity.inv.InvWarningConfig;

/**
 * PurReplenishmentSuggestion 服务实现
 */
@Service
@RequiredArgsConstructor
public class PurReplenishmentSuggestionServiceImpl extends ServiceImpl<PurReplenishmentSuggestionMapper, PurReplenishmentSuggestion> implements PurReplenishmentSuggestionService {
    private final InvInventoryService inventoryService;
    private final InvWarningConfigService warningConfigService;
    private final PurPurchaseRequisitionService requisitionService;


    // ==== Business Logic Methods ====

    @Override
    public int scanAndGenerate() {
        int count = 0;
        java.util.List<InvInventory> inventories = inventoryService.list();
        for (InvInventory inv : inventories) {
            java.util.List<InvWarningConfig> configs = warningConfigService.list(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<InvWarningConfig>()
                    .eq(InvWarningConfig::getProductId, inv.getProductId())
                    .eq(InvWarningConfig::getWarehouseId, inv.getWarehouseId())
            );
            for (InvWarningConfig cfg : configs) {
                if (cfg.getMinQuantity() != null && inv.getQuantity().compareTo(cfg.getMinQuantity()) < 0) {
                    PurReplenishmentSuggestion sug = new PurReplenishmentSuggestion();
                    sug.setProductId(inv.getProductId());
                    sug.setWarehouseId(inv.getWarehouseId());
                    sug.setCurrentQuantity(inv.getQuantity());
                    sug.setSafetyQuantity(cfg.getMinQuantity());
                    sug.setSuggestedQuantity(cfg.getMinQuantity().subtract(inv.getQuantity()));
                    // 简单估算日均销量（近30天）/ 采购提前期默认7天
                    sug.setAvgDailySales(java.math.BigDecimal.ZERO);
                    sug.setLeadTimeDays(7);
                    sug.setSuggestedDate(java.time.LocalDate.now());
                    sug.setStatus(0);
                    save(sug);
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Long convertToRequisition(Long id) {
        PurReplenishmentSuggestion sug = getById(id);
        if (sug == null) throw new RuntimeException("补货建议不存在");
        sug.setStatus(1);
        updateById(sug);
        PurPurchaseRequisition req = new PurPurchaseRequisition();
        req.setRequisitionNo("PR-" + System.currentTimeMillis());
        req.setRequisitionDate(java.time.LocalDate.now());
        req.setStatus(0);
        requisitionService.save(req);
        return req.getId();
    }

    @Override
    public void ignore(Long id) {
        PurReplenishmentSuggestion sug = getById(id);
        if (sug == null) throw new RuntimeException("补货建议不存在");
        sug.setStatus(2);
        updateById(sug);
    }

}
