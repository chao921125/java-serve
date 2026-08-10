package com.cc.app.controller.inv;

import com.cc.core.entity.bas.BasProduct;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.service.bas.BasProductService;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 扫码操作控制器
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScanController {

    private final BasProductService productService;
    private final InvInventoryService inventoryService;

    /**
     * 扫码查商品
     */
    @GetMapping("/products/barcode/{barcode}")
    public R<Map<String, Object>> getByBarcode(@PathVariable String barcode) {
        BasProduct product = productService.lambdaQuery()
                .eq(BasProduct::getBarcode, barcode).one();
        if (product == null) {
            return R.fail("未找到条码对应的商品");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("product", product);
        // 查询各仓库库存
        var inventories = inventoryService.lambdaQuery()
                .eq(InvInventory::getProductId, product.getId()).list();
        result.put("inventories", inventories);
        return R.ok(result);
    }

    /**
     * 扫码入库
     */
    @PostMapping("/inventory/scan-in")
    public R<Map<String, Object>> scanIn(@RequestParam String barcode,
            @RequestParam Long warehouseId,
            @RequestParam BigDecimal quantity) {
        BasProduct product = productService.lambdaQuery()
                .eq(BasProduct::getBarcode, barcode).one();
        if (product == null) {
            return R.fail("未找到条码对应的商品");
        }
        inventoryService.increaseStock(product.getId(), warehouseId, quantity, "");
        Map<String, Object> result = new HashMap<>();
        result.put("productName", product.getName());
        result.put("quantity", quantity);
        return R.ok(result);
    }

    /**
     * 扫码出库
     */
    @PostMapping("/inventory/scan-out")
    public R<Map<String, Object>> scanOut(@RequestParam String barcode,
            @RequestParam Long warehouseId,
            @RequestParam BigDecimal quantity) {
        BasProduct product = productService.lambdaQuery()
                .eq(BasProduct::getBarcode, barcode).one();
        if (product == null) {
            return R.fail("未找到条码对应的商品");
        }
        inventoryService.decreaseStock(product.getId(), warehouseId, quantity, "");
        Map<String, Object> result = new HashMap<>();
        result.put("productName", product.getName());
        result.put("quantity", quantity);
        return R.ok(result);
    }

    /**
     * 扫码盘点
     */
    @PostMapping("/inventory/scan-check")
    public R<Map<String, Object>> scanCheck(@RequestParam String barcode,
            @RequestParam Long warehouseId,
            @RequestParam BigDecimal actualQuantity) {
        BasProduct product = productService.lambdaQuery()
                .eq(BasProduct::getBarcode, barcode).one();
        if (product == null) {
            return R.fail("未找到条码对应的商品");
        }
        InvInventory inventory = inventoryService.lambdaQuery()
                .eq(InvInventory::getProductId, product.getId())
                .eq(InvInventory::getWarehouseId, warehouseId).one();
        Map<String, Object> result = new HashMap<>();
        result.put("productName", product.getName());
        result.put("systemQuantity", inventory != null ? inventory.getQuantity() : BigDecimal.ZERO);
        result.put("actualQuantity", actualQuantity);
        result.put("difference", actualQuantity.subtract(
                inventory != null ? inventory.getQuantity() : BigDecimal.ZERO));
        return R.ok(result);
    }
}
