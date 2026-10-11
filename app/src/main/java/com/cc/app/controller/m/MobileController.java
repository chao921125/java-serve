// AI生成
package com.cc.app.controller.m;

import com.cc.core.entity.bas.BasProduct;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.pur.PurPurchaseOrder;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.service.bas.BasProductService;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.core.service.pur.PurPurchaseOrderService;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * 移动端控制器 — 复用现有业务 Service，提供精简版 API
 */
@RestController
@RequestMapping("/api/m")
@RequiredArgsConstructor
public class MobileController {

    private final BasProductService productService;
    private final SalSalesOrderService salesOrderService;
    private final InvInventoryService inventoryService;
    private final PurPurchaseOrderService purchaseOrderService;

    /**
     * 移动端首页数据
     */
    @GetMapping("/dashboard")
    public R<Map<String, Object>> dashboard() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("todaySales", salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, java.time.LocalDate.now()).count());
        result.put("monthSales", salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, java.time.LocalDate.now().withDayOfMonth(1)).count());
        result.put("pendingApprovals", salesOrderService.lambdaQuery()
                .eq(SalSalesOrder::getStatus, 1).count());
        result.put("stockWarnings", productService.lambdaQuery()
                .le(BasProduct::getInventory, 10)
                .eq(BasProduct::getStatus, 0).count());
        result.put("notifications", salesOrderService.lambdaQuery()
                .eq(SalSalesOrder::getStatus, 1).count());
        return R.ok(result);
    }

    /**
     * 商品搜索（关键字+扫码）
     */
    @GetMapping("/products/search")
    public R<?> searchProducts(@RequestParam String keyword) {
        return R.ok(productService.lambdaQuery()
                .and(w -> w.like(BasProduct::getBarcode, keyword)
                          .or().like(BasProduct::getName, keyword))
                .eq(BasProduct::getStatus, 0)
                .last("LIMIT 20")
                .list());
    }

    /**
     * 查询商品库存（多仓库）
     */
    @GetMapping("/products/{id}/stock")
    public R<List<InvInventory>> productStock(@PathVariable Long id) {
        return R.ok(inventoryService.lambdaQuery()
                .eq(InvInventory::getProductId, id)
                .gt(InvInventory::getQuantity, 0)
                .list());
    }

    /**
     * 快速开销售单
     */
    @PostMapping("/sales-orders")
    public R<Void> quickSalesOrder(@RequestBody SalSalesOrder order) {
        order.setStatus(0);
        salesOrderService.save(order);
        return R.ok();
    }

    /**
     * 销售订单列表（移动端简化字段）
     */
    @GetMapping("/sales-orders")
    public R<List<SalSalesOrder>> salesOrderList() {
        return R.ok(salesOrderService.lambdaQuery()
                .orderByDesc(SalSalesOrder::getCreateTime)
                .last("LIMIT 50")
                .list());
    }

    /**
     * 快速开采购单
     */
    @PostMapping("/purchase-orders")
    public R<Map<String, Object>> quickPurchaseOrder(@RequestBody Map<String, Object> request) {
        PurPurchaseOrder order = new PurPurchaseOrder();
        order.setOrderNo("PUR" + System.currentTimeMillis());
        order.setOrderDate(LocalDate.now());
        order.setSupplierId(request.get("supplierId") != null
                ? Long.valueOf(request.get("supplierId").toString()) : null);
        order.setWarehouseId(request.get("warehouseId") != null
                ? Long.valueOf(request.get("warehouseId").toString()) : 1L);
        order.setStatus(0);
        order.setTotalQuantity(BigDecimal.ZERO);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setTotalTax(BigDecimal.ZERO);
        order.setTotalPayable(BigDecimal.ZERO);
        order.setReceivedQuantity(BigDecimal.ZERO);
        purchaseOrderService.save(order);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("orderNo", order.getOrderNo());
        result.put("orderId", order.getId());
        return R.ok(result);
    }

    /**
     * 移动端审批
     */
    @PostMapping("/approvals/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        salesOrderService.approve(id);
        return R.ok();
    }

    /**
     * 我的待审列表
     */
    @GetMapping("/my-approvals")
    public R<List<SalSalesOrder>> myApprovals() {
        return R.ok(salesOrderService.lambdaQuery()
                .eq(SalSalesOrder::getStatus, 1)
                .orderByDesc(SalSalesOrder::getCreateTime)
                .list());
    }

    /**
     * 库存速查（扫码）
     */
    @GetMapping("/inventory/check")
    public R<List<InvInventory>> inventoryCheck(@RequestParam String barcode) {
        BasProduct product = productService.lambdaQuery()
                .eq(BasProduct::getBarcode, barcode).one();
        if (product == null) return R.fail("未找到商品");
        return R.ok(inventoryService.lambdaQuery()
                .eq(InvInventory::getProductId, product.getId()).list());
    }

    /**
     * 扫码获取商品信息+库存
     */
    @PostMapping("/scan/barcode")
    public R<Map<String, Object>> scanBarcode(@RequestParam String barcode) {
        BasProduct product = productService.lambdaQuery()
                .eq(BasProduct::getBarcode, barcode).one();
        if (product == null) return R.fail("商品不存在");
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("product", product);
        result.put("inventories", inventoryService.lambdaQuery()
                .eq(InvInventory::getProductId, product.getId()).list());
        return R.ok(result);
    }
}
