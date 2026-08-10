package com.cc.app.controller.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.inv.InventoryQueryDTO;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.inv.InvWarningConfig;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.core.service.inv.InvWarningConfigService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存控制器
 */
@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InvInventoryController {

    private final InvInventoryService inventoryService;
    private final InvWarningConfigService warningConfigService;

    /**
     * 分页查询库存
     */
    @GetMapping
    public R<IPage<InvInventory>> list(InventoryQueryDTO query) {
        return R.ok(inventoryService.page(query));
    }

    /**
     * 库存预警列表
     */
    @GetMapping("/warnings")
    public R<List<Map<String, Object>>> warnings() {
        List<InvWarningConfig> configs = warningConfigService.list(
                new LambdaQueryWrapper<InvWarningConfig>().eq(InvWarningConfig::getStatus, 0)
        );
        List<Map<String, Object>> result = configs.stream().map(config -> {
            Map<String, Object> map = new HashMap<>();
            map.put("config", config);
            InvInventory inv = inventoryService.getOne(
                    new LambdaQueryWrapper<InvInventory>()
                            .eq(InvInventory::getWarehouseId, config.getWarehouseId())
                            .eq(InvInventory::getProductId, config.getProductId())
                            .last("LIMIT 1")
            );
            if (inv != null) {
                map.put("inventory", inv);
                BigDecimal qty = inv.getQuantity() != null ? inv.getQuantity() : BigDecimal.ZERO;
                boolean isWarning = false;
                if (config.getMinQuantity() != null && qty.compareTo(config.getMinQuantity()) < 0) {
                    isWarning = true;
                }
                if (config.getMaxQuantity() != null && qty.compareTo(config.getMaxQuantity()) > 0) {
                    isWarning = true;
                }
                map.put("isWarning", isWarning);
            } else {
                map.put("inventory", null);
                map.put("isWarning", config.getMinQuantity() != null
                        && config.getMinQuantity().compareTo(BigDecimal.ZERO) > 0);
            }
            return map;
        }).toList();
        return R.ok(result);
    }

    /**
     * 商品库存分布
     */
    @GetMapping("/distribution")
    public R<List<InvInventory>> distribution(@RequestParam Long productId) {
        return R.ok(inventoryService.getByProduct(productId));
    }

    /**
     * 库存详情
     */
    @GetMapping("/{id}")
    public R<InvInventory> detail(@PathVariable Long id) {
        InvInventory inventory = inventoryService.getById(id);
        if (inventory == null) {
            return R.fail("库存记录不存在");
        }
        return R.ok(inventory);
    }
}
