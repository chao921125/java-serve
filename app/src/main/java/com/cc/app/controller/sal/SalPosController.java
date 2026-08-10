package com.cc.app.controller.sal;

import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * POS 收银控制器 — 轻量零售收银
 */
@RestController
@RequestMapping("/api/v1/pos")
@RequiredArgsConstructor
public class SalPosController {

    private final SalSalesOrderService salesOrderService;

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
        order.setStatus(0);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setTotalReceivable(BigDecimal.ZERO);
        order.setTotalTax(BigDecimal.ZERO);
        order.setTotalQuantity(BigDecimal.ZERO);
        order.setDeliveredQuantity(BigDecimal.ZERO);
        salesOrderService.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        if (request.get("totalAmount") != null) {
            totalAmount = new BigDecimal(request.get("totalAmount").toString());
        }
        BigDecimal receivedAmount = BigDecimal.ZERO;
        if (request.get("receivedAmount") != null) {
            receivedAmount = new BigDecimal(request.get("receivedAmount").toString());
        }
        BigDecimal changeAmount = receivedAmount.subtract(totalAmount);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("orderNo", order.getOrderNo());
        result.put("orderId", order.getId());
        result.put("totalAmount", totalAmount);
        result.put("changeAmount", changeAmount.compareTo(BigDecimal.ZERO) < 0
                ? BigDecimal.ZERO : changeAmount);
        return R.ok(result);
    }

    /**
     * POS 日结
     */
    @GetMapping("/daily-summary/{storeId}")
    public R<Map<String, Object>> dailySummary(@PathVariable Long storeId) {
        return R.ok(Map.of(
                "storeId", storeId,
                "totalSales", BigDecimal.ZERO,
                "orderCount", 0,
                "cashAmount", BigDecimal.ZERO,
                "wechatAmount", BigDecimal.ZERO,
                "alipayAmount", BigDecimal.ZERO
        ));
    }

    /**
     * POS 日清
     */
    @PostMapping("/daily-close/{storeId}")
    public R<Void> dailyClose(@PathVariable Long storeId) {
        return R.ok();
    }
}
