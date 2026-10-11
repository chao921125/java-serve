// AI生成
package com.cc.app.controller.sal;

import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.entity.sal.SalSalesOrderItem;
import com.cc.core.service.sal.SalSalesOrderItemService;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * POS 收银控制器 — 轻量零售收银
 */
@RestController
@RequestMapping("/api/v1/pos")
@RequiredArgsConstructor
public class SalPosController {

    private final SalSalesOrderService salesOrderService;
    private final SalSalesOrderItemService salesOrderItemService;

    /**
     * POS 收银结算
     */
    @PostMapping("/checkout")
    public R<Map<String, Object>> checkout(@RequestBody Map<String, Object> request) {
        SalSalesOrder order = new SalSalesOrder();
        order.setOrderNo("POS" + System.currentTimeMillis());
        order.setOrderDate(LocalDate.now());
        order.setCustomerId(request.get("customerId") != null
                ? Long.valueOf(request.get("customerId").toString()) : null);
        order.setWarehouseId(request.get("storeId") != null
                ? Long.valueOf(request.get("storeId").toString()) : 1L);
        order.setStatus(2);
        order.setIsSuspended(0);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");

        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;

        List<SalSalesOrderItem> orderItems = new ArrayList<>();
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                Map<String, Object> item = items.get(i);
                SalSalesOrderItem orderItem = new SalSalesOrderItem();
                orderItem.setProductId(item.get("productId") != null
                        ? Long.valueOf(item.get("productId").toString()) : null);
                orderItem.setQuantity(new BigDecimal(item.getOrDefault("quantity", "1").toString()));
                orderItem.setPrice(new BigDecimal(item.getOrDefault("price", "0").toString()));
                BigDecimal taxRate = new BigDecimal(item.getOrDefault("taxRate", "0").toString());
                orderItem.setTaxRate(taxRate);

                BigDecimal lineTotal = orderItem.getPrice().multiply(orderItem.getQuantity());
                BigDecimal amount = taxRate.compareTo(BigDecimal.ZERO) > 0
                        ? lineTotal.divide(BigDecimal.ONE.add(taxRate.divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP)), 2, BigDecimal.ROUND_HALF_UP)
                        : lineTotal;
                BigDecimal taxAmount = lineTotal.subtract(amount);
                orderItem.setAmount(amount);
                orderItem.setTaxAmount(taxAmount);
                orderItem.setTotalAmount(lineTotal);
                orderItem.setDeliveredQuantity(BigDecimal.ZERO);
                orderItem.setSort(i + 1);
                orderItems.add(orderItem);

                totalQuantity = totalQuantity.add(orderItem.getQuantity());
                totalAmount = totalAmount.add(amount);
                totalTax = totalTax.add(taxAmount);
            }
        }

        order.setTotalQuantity(totalQuantity);
        order.setTotalAmount(totalAmount);
        order.setTotalTax(totalTax);
        order.setTotalReceivable(totalAmount.add(totalTax));
        order.setDeliveredQuantity(BigDecimal.ZERO);
        salesOrderService.save(order);

        for (SalSalesOrderItem item : orderItems) {
            item.setOrderId(order.getId());
            salesOrderItemService.save(item);
        }

        BigDecimal receivedAmount = BigDecimal.ZERO;
        if (request.get("receivedAmount") != null) {
            receivedAmount = new BigDecimal(request.get("receivedAmount").toString());
        }
        BigDecimal changeAmount = receivedAmount.subtract(order.getTotalReceivable());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("orderNo", order.getOrderNo());
        result.put("orderId", order.getId());
        result.put("totalAmount", order.getTotalAmount());
        result.put("totalTax", order.getTotalTax());
        result.put("totalReceivable", order.getTotalReceivable());
        result.put("totalQuantity", order.getTotalQuantity());
        result.put("receivedAmount", receivedAmount);
        result.put("changeAmount", changeAmount.compareTo(BigDecimal.ZERO) < 0
                ? BigDecimal.ZERO : changeAmount);
        return R.ok(result);
    }

    /**
     * POS 日结
     */
    @GetMapping("/daily-summary/{storeId}")
    public R<Map<String, Object>> dailySummary(@PathVariable Long storeId) {
        LocalDate today = LocalDate.now();
        List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                .likeRight(SalSalesOrder::getOrderNo, "POS")
                .eq(SalSalesOrder::getWarehouseId, storeId)
                .eq(SalSalesOrder::getOrderDate, today)
                .ge(SalSalesOrder::getStatus, 2).list();

        BigDecimal totalSales = BigDecimal.ZERO;
        for (SalSalesOrder order : orders) {
            if (order.getTotalReceivable() != null) {
                totalSales = totalSales.add(order.getTotalReceivable());
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("storeId", storeId);
        result.put("date", today.toString());
        result.put("totalSales", totalSales);
        result.put("orderCount", orders.size());
        result.put("cashAmount", totalSales);
        result.put("wechatAmount", BigDecimal.ZERO);
        result.put("alipayAmount", BigDecimal.ZERO);
        return R.ok(result);
    }

    /**
     * POS 日清
     */
    @PostMapping("/daily-close/{storeId}")
    public R<Map<String, Object>> dailyClose(@PathVariable Long storeId) {
        LocalDate today = LocalDate.now();
        List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                .likeRight(SalSalesOrder::getOrderNo, "POS")
                .eq(SalSalesOrder::getWarehouseId, storeId)
                .eq(SalSalesOrder::getOrderDate, today)
                .lt(SalSalesOrder::getStatus, 4).list();

        for (SalSalesOrder order : orders) {
            order.setStatus(4);
            salesOrderService.updateById(order);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("storeId", storeId);
        result.put("date", today.toString());
        result.put("closedCount", orders.size());
        return R.ok(result);
    }
}
