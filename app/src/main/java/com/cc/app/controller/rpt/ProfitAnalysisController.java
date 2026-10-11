// AI生成
package com.cc.app.controller.rpt;

import com.cc.core.entity.bas.BasProduct;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.entity.sal.SalSalesOrderItem;
import com.cc.core.service.bas.BasProductService;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.core.service.sal.SalSalesOrderItemService;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 利润分析控制器
 */
@RestController
@RequestMapping("/api/v1/profit-analysis")
@RequiredArgsConstructor
public class ProfitAnalysisController {

    private final SalSalesOrderService salesOrderService;
    private final SalSalesOrderItemService salesOrderItemService;
    private final InvInventoryService inventoryService;
    private final BasProductService productService;

    /**
     * 商品毛利分析
     */
    @GetMapping("/product")
    public R<List<Map<String, Object>>> productProfit(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : LocalDate.now().withDayOfMonth(1);
        LocalDate end = endDate != null ? LocalDate.parse(endDate).plusDays(1) : LocalDate.now().plusDays(1);

        List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, start)
                .lt(SalSalesOrder::getOrderDate, end)
                .ge(SalSalesOrder::getStatus, 2).list();
        if (orders.isEmpty()) {
            return R.ok(List.of());
        }
        Set<Long> orderIds = orders.stream().map(SalSalesOrder::getId).collect(Collectors.toSet());
        List<SalSalesOrderItem> items = salesOrderItemService.lambdaQuery()
                .in(SalSalesOrderItem::getOrderId, orderIds).list();

        Map<Long, BigDecimal[]> productMap = new HashMap<>();
        for (SalSalesOrderItem item : items) {
            BigDecimal amount = item.getAmount() != null ? item.getAmount() : BigDecimal.ZERO;
            BigDecimal cost = calcItemCost(item);
            productMap.computeIfAbsent(item.getProductId(), k -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            productMap.get(item.getProductId())[0] = productMap.get(item.getProductId())[0].add(amount);
            productMap.get(item.getProductId())[1] = productMap.get(item.getProductId())[1].add(cost);
        }

        List<Map<String, Object>> result = productMap.entrySet().stream()
                .sorted((a, b) -> b.getValue()[0].subtract(b.getValue()[1]).compareTo(a.getValue()[0].subtract(a.getValue()[1])))
                .map(e -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    BasProduct product = productService.getById(e.getKey());
                    BigDecimal salesAmount = e.getValue()[0];
                    BigDecimal costAmount = e.getValue()[1];
                    BigDecimal grossProfit = salesAmount.subtract(costAmount);
                    m.put("productId", e.getKey());
                    m.put("productName", product != null ? product.getName() : "未知商品");
                    m.put("salesAmount", salesAmount);
                    m.put("costAmount", costAmount);
                    m.put("grossProfit", grossProfit);
                    m.put("grossMargin", salesAmount.compareTo(BigDecimal.ZERO) > 0
                            ? grossProfit.multiply(BigDecimal.valueOf(100)).divide(salesAmount, 2, RoundingMode.HALF_UP) + "%"
                            : "0%");
                    return m;
                })
                .collect(Collectors.toList());
        return R.ok(result);
    }

    /**
     * 客户利润贡献
     */
    @GetMapping("/customer")
    public R<List<Map<String, Object>>> customerProfit(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : LocalDate.now().withDayOfMonth(1);
        LocalDate end = endDate != null ? LocalDate.parse(endDate).plusDays(1) : LocalDate.now().plusDays(1);

        List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, start)
                .lt(SalSalesOrder::getOrderDate, end)
                .ge(SalSalesOrder::getStatus, 2).list();
        if (orders.isEmpty()) {
            return R.ok(List.of());
        }
        Set<Long> orderIds = orders.stream().map(SalSalesOrder::getId).collect(Collectors.toSet());
        List<SalSalesOrderItem> items = salesOrderItemService.lambdaQuery()
                .in(SalSalesOrderItem::getOrderId, orderIds).list();
        Map<Long, BigDecimal> orderCostMap = new HashMap<>();
        for (SalSalesOrderItem item : items) {
            orderCostMap.merge(item.getOrderId(), calcItemCost(item), BigDecimal::add);
        }

        Map<Long, BigDecimal[]> customerMap = new HashMap<>();
        for (SalSalesOrder order : orders) {
            if (order.getCustomerId() == null) continue;
            BigDecimal amount = order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO;
            BigDecimal cost = orderCostMap.getOrDefault(order.getId(), BigDecimal.ZERO);
            customerMap.computeIfAbsent(order.getCustomerId(), k -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            customerMap.get(order.getCustomerId())[0] = customerMap.get(order.getCustomerId())[0].add(amount);
            customerMap.get(order.getCustomerId())[1] = customerMap.get(order.getCustomerId())[1].add(cost);
        }

        List<Map<String, Object>> result = customerMap.entrySet().stream()
                .sorted((a, b) -> b.getValue()[0].subtract(b.getValue()[1]).compareTo(a.getValue()[0].subtract(a.getValue()[1])))
                .map(e -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    BigDecimal salesAmount = e.getValue()[0];
                    BigDecimal costAmount = e.getValue()[1];
                    BigDecimal grossProfit = salesAmount.subtract(costAmount);
                    m.put("customerId", e.getKey());
                    m.put("salesAmount", salesAmount);
                    m.put("costAmount", costAmount);
                    m.put("grossProfit", grossProfit);
                    m.put("grossMargin", salesAmount.compareTo(BigDecimal.ZERO) > 0
                            ? grossProfit.multiply(BigDecimal.valueOf(100)).divide(salesAmount, 2, RoundingMode.HALF_UP) + "%"
                            : "0%");
                    return m;
                })
                .collect(Collectors.toList());
        return R.ok(result);
    }

    /**
     * 业务员利润
     */
    @GetMapping("/salesperson")
    public R<List<Map<String, Object>>> salespersonProfit(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : LocalDate.now().withDayOfMonth(1);
        LocalDate end = endDate != null ? LocalDate.parse(endDate).plusDays(1) : LocalDate.now().plusDays(1);

        List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, start)
                .lt(SalSalesOrder::getOrderDate, end)
                .ge(SalSalesOrder::getStatus, 2).list();
        if (orders.isEmpty()) {
            return R.ok(List.of());
        }
        Set<Long> orderIds = orders.stream().map(SalSalesOrder::getId).collect(Collectors.toSet());
        List<SalSalesOrderItem> items = salesOrderItemService.lambdaQuery()
                .in(SalSalesOrderItem::getOrderId, orderIds).list();
        Map<Long, BigDecimal> orderCostMap = new HashMap<>();
        for (SalSalesOrderItem item : items) {
            orderCostMap.merge(item.getOrderId(), calcItemCost(item), BigDecimal::add);
        }

        Map<String, BigDecimal[]> personMap = new HashMap<>();
        for (SalSalesOrder order : orders) {
            String salesperson = order.getCreateBy() != null ? order.getCreateBy() : "未知";
            BigDecimal amount = order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO;
            BigDecimal cost = orderCostMap.getOrDefault(order.getId(), BigDecimal.ZERO);
            personMap.computeIfAbsent(salesperson, k -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            personMap.get(salesperson)[0] = personMap.get(salesperson)[0].add(amount);
            personMap.get(salesperson)[1] = personMap.get(salesperson)[1].add(cost);
        }

        List<Map<String, Object>> result = personMap.entrySet().stream()
                .sorted((a, b) -> b.getValue()[0].subtract(b.getValue()[1]).compareTo(a.getValue()[0].subtract(a.getValue()[1])))
                .map(e -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    BigDecimal salesAmount = e.getValue()[0];
                    BigDecimal costAmount = e.getValue()[1];
                    BigDecimal grossProfit = salesAmount.subtract(costAmount);
                    m.put("salesperson", e.getKey());
                    m.put("salesAmount", salesAmount);
                    m.put("costAmount", costAmount);
                    m.put("grossProfit", grossProfit);
                    m.put("grossMargin", salesAmount.compareTo(BigDecimal.ZERO) > 0
                            ? grossProfit.multiply(BigDecimal.valueOf(100)).divide(salesAmount, 2, RoundingMode.HALF_UP) + "%"
                            : "0%");
                    return m;
                })
                .collect(Collectors.toList());
        return R.ok(result);
    }

    /**
     * 月度利润趋势
     */
    @GetMapping("/monthly")
    public R<List<Map<String, Object>>> monthlyProfit(@RequestParam(defaultValue = "12") Integer months) {
        LocalDate today = LocalDate.now();
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = months - 1; i >= 0; i--) {
            LocalDate monthStart = today.minusMonths(i).withDayOfMonth(1);
            LocalDate monthEnd = monthStart.plusMonths(1);
            List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                    .ge(SalSalesOrder::getOrderDate, monthStart)
                    .lt(SalSalesOrder::getOrderDate, monthEnd)
                    .ge(SalSalesOrder::getStatus, 2).list();
            BigDecimal salesAmount = BigDecimal.ZERO;
            BigDecimal costAmount = BigDecimal.ZERO;
            if (!orders.isEmpty()) {
                Set<Long> orderIds = orders.stream().map(SalSalesOrder::getId).collect(Collectors.toSet());
                List<SalSalesOrderItem> items = salesOrderItemService.lambdaQuery()
                        .in(SalSalesOrderItem::getOrderId, orderIds).list();
                for (SalSalesOrderItem item : items) {
                    salesAmount = salesAmount.add(item.getAmount() != null ? item.getAmount() : BigDecimal.ZERO);
                    costAmount = costAmount.add(calcItemCost(item));
                }
            }
            BigDecimal grossProfit = salesAmount.subtract(costAmount);
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("month", monthStart.toString().substring(0, 7));
            m.put("salesAmount", salesAmount);
            m.put("costAmount", costAmount);
            m.put("grossProfit", grossProfit);
            m.put("grossMargin", salesAmount.compareTo(BigDecimal.ZERO) > 0
                    ? grossProfit.multiply(BigDecimal.valueOf(100)).divide(salesAmount, 2, RoundingMode.HALF_UP) + "%"
                    : "0%");
            result.add(m);
        }
        return R.ok(result);
    }

    private BigDecimal calcItemCost(SalSalesOrderItem item) {
        if (item.getProductId() == null || item.getQuantity() == null) {
            return BigDecimal.ZERO;
        }
        InvInventory inv = inventoryService.lambdaQuery()
                .eq(InvInventory::getProductId, item.getProductId()).one();
        if (inv != null && inv.getCostPrice() != null) {
            return inv.getCostPrice().multiply(item.getQuantity());
        }
        return BigDecimal.ZERO;
    }
}
