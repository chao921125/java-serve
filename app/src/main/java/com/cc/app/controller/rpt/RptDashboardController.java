// AI生成
package com.cc.app.controller.rpt;

import com.cc.core.entity.bas.BasProduct;
import com.cc.core.entity.fin.FinPayable;
import com.cc.core.entity.fin.FinReceivable;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.entity.sal.SalSalesOrderItem;
import com.cc.core.service.bas.BasProductService;
import com.cc.core.service.fin.FinPayableService;
import com.cc.core.service.fin.FinReceivableService;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.core.service.sal.SalSalesOrderItemService;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 经营看板/驾驶舱控制器
 * 提供首页数据大屏所需的聚合统计数据
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class RptDashboardController {

    private final SalSalesOrderService salesOrderService;
    private final SalSalesOrderItemService salesOrderItemService;
    private final InvInventoryService inventoryService;
    private final FinReceivableService receivableService;
    private final FinPayableService payableService;
    private final BasProductService productService;

    /**
     * 首页数据总览
     */
    @GetMapping("/summary")
    public R<Map<String, Object>> summary() {
        Map<String, Object> result = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);

        // 今日/本月销售订单数
        long todayOrderCount = salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, today).count();
        long monthOrderCount = salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, monthStart).count();

        // 今日/本月销售额（已审核及以上状态的订单）
        BigDecimal todaySalesAmount = sumAmount(salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, today)
                .ge(SalSalesOrder::getStatus, 2).list());
        BigDecimal monthSalesAmount = sumAmount(salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, monthStart)
                .ge(SalSalesOrder::getStatus, 2).list());

        // 库存SKU数量和库存价值
        List<InvInventory> inventories = inventoryService.lambdaQuery()
                .gt(InvInventory::getQuantity, 0).list();
        long inventorySkuCount = inventories.size();
        BigDecimal inventoryValue = inventories.stream()
                .map(InvInventory::getTotalCost)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 应收余额
        BigDecimal receivableAmount = receivableService.lambdaQuery()
                .lt(FinReceivable::getStatus, 2).list().stream()
                .map(FinReceivable::getBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 应付余额
        BigDecimal payableAmount = payableService.lambdaQuery()
                .lt(FinPayable::getStatus, 2).list().stream()
                .map(FinPayable::getBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 低库存商品数（库存数量 <= 10）
        long lowStockCount = productService.lambdaQuery()
                .le(BasProduct::getInventory, 10)
                .eq(BasProduct::getStatus, 0).count();

        // 临期预警数（30天内过期）
        long expiryAlertCount = inventoryService.lambdaQuery()
                .gt(InvInventory::getQuantity, 0)
                .le(InvInventory::getExpiryDate, today.plusDays(30))
                .count();

        // 今日/本月利润（销售额 - 销售成本，基于成本计算记录）
        BigDecimal todayProfit = calcProfit(today, today.plusDays(1));
        BigDecimal monthProfit = calcProfit(monthStart, today.plusDays(1));

        result.put("todaySales", todaySalesAmount);
        result.put("todayProfit", todayProfit);
        result.put("monthSales", monthSalesAmount);
        result.put("monthProfit", monthProfit);
        result.put("inventoryValue", inventoryValue);
        result.put("receivableAmount", receivableAmount);
        result.put("payableAmount", payableAmount);
        result.put("lowStockCount", lowStockCount);
        result.put("expiryAlertCount", expiryAlertCount);
        result.put("inventorySkuCount", inventorySkuCount);
        result.put("pendingOrderCount", salesOrderService.lambdaQuery()
                .eq(SalSalesOrder::getStatus, 1).count());
        return R.ok(result);
    }

    /**
     * 销售趋势（近30天）
     */
    @GetMapping("/sales-trend")
    public R<List<Map<String, Object>>> salesTrend(
            @RequestParam(defaultValue = "day") String granularity) {
        List<Map<String, Object>> trend = new ArrayList<>();
        for (int i = 29; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                    .ge(SalSalesOrder::getOrderDate, date)
                    .lt(SalSalesOrder::getOrderDate, date.plusDays(1))
                    .ge(SalSalesOrder::getStatus, 2).list();
            Map<String, Object> point = new LinkedHashMap<>();
            point.put("date", date.toString());
            point.put("count", orders.size());
            point.put("amount", sumAmount(orders));
            trend.add(point);
        }
        return R.ok(trend);
    }

    /**
     * 热销商品 TOP10
     */
    @GetMapping("/top-products")
    public R<List<Map<String, Object>>> topProducts() {
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, monthStart)
                .ge(SalSalesOrder::getStatus, 2).list();
        if (orders.isEmpty()) {
            return R.ok(List.of());
        }
        Set<Long> orderIds = orders.stream().map(SalSalesOrder::getId).collect(Collectors.toSet());
        List<SalSalesOrderItem> items = salesOrderItemService.lambdaQuery()
                .in(SalSalesOrderItem::getOrderId, orderIds).list();

        Map<Long, BigDecimal> productAmountMap = new HashMap<>();
        Map<Long, BigDecimal> productQtyMap = new HashMap<>();
        for (SalSalesOrderItem item : items) {
            productAmountMap.merge(item.getProductId(),
                    item.getTotalAmount() != null ? item.getTotalAmount() : BigDecimal.ZERO, BigDecimal::add);
            productQtyMap.merge(item.getProductId(),
                    item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO, BigDecimal::add);
        }

        List<Map<String, Object>> result = productAmountMap.entrySet().stream()
                .sorted(Map.Entry.<Long, BigDecimal>comparingByValue().reversed())
                .limit(10)
                .map(e -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    BasProduct product = productService.getById(e.getKey());
                    m.put("productId", e.getKey());
                    m.put("productName", product != null ? product.getName() : "未知商品");
                    m.put("salesAmount", e.getValue());
                    m.put("salesQuantity", productQtyMap.getOrDefault(e.getKey(), BigDecimal.ZERO));
                    return m;
                })
                .collect(Collectors.toList());
        return R.ok(result);
    }

    /**
     * 客户贡献 TOP10
     */
    @GetMapping("/top-customers")
    public R<List<Map<String, Object>>> topCustomers() {
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, monthStart)
                .ge(SalSalesOrder::getStatus, 2).list();

        Map<Long, BigDecimal> customerAmountMap = new HashMap<>();
        Map<Long, Integer> customerOrderCountMap = new HashMap<>();
        for (SalSalesOrder order : orders) {
            if (order.getCustomerId() != null) {
                customerAmountMap.merge(order.getCustomerId(),
                        order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO, BigDecimal::add);
                customerOrderCountMap.merge(order.getCustomerId(), 1, Integer::sum);
            }
        }

        List<Map<String, Object>> result = customerAmountMap.entrySet().stream()
                .sorted(Map.Entry.<Long, BigDecimal>comparingByValue().reversed())
                .limit(10)
                .map(e -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("customerId", e.getKey());
                    m.put("salesAmount", e.getValue());
                    m.put("orderCount", customerOrderCountMap.getOrDefault(e.getKey(), 0));
                    return m;
                })
                .collect(Collectors.toList());
        return R.ok(result);
    }

    /**
     * 库存预警汇总
     */
    @GetMapping("/stock-warning")
    public R<Map<String, Object>> stockWarning() {
        LocalDate today = LocalDate.now();
        long lowStockCount = productService.lambdaQuery()
                .le(BasProduct::getInventory, 10)
                .eq(BasProduct::getStatus, 0).count();
        long highStockCount = productService.lambdaQuery()
                .ge(BasProduct::getInventory, 1000)
                .eq(BasProduct::getStatus, 0).count();
        long expiryCount = inventoryService.lambdaQuery()
                .gt(InvInventory::getQuantity, 0)
                .le(InvInventory::getExpiryDate, today.plusDays(30))
                .count();
        return R.ok(Map.of(
                "lowStockCount", lowStockCount,
                "highStockCount", highStockCount,
                "expiryCount", expiryCount
        ));
    }

    /**
     * 应收预警汇总
     */
    @GetMapping("/receivable-warning")
    public R<Map<String, Object>> receivableWarning() {
        LocalDate today = LocalDate.now();
        List<FinReceivable> overdueList = receivableService.lambdaQuery()
                .lt(FinReceivable::getStatus, 2)
                .lt(FinReceivable::getDueDate, today).list();
        BigDecimal overdueAmount = overdueList.stream()
                .map(FinReceivable::getBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return R.ok(Map.of(
                "overdueCount", overdueList.size(),
                "overdueAmount", overdueAmount
        ));
    }

    private BigDecimal sumAmount(List<SalSalesOrder> orders) {
        return orders.stream()
                .map(SalSalesOrder::getTotalAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcProfit(LocalDate start, LocalDate end) {
        List<SalSalesOrder> orders = salesOrderService.lambdaQuery()
                .ge(SalSalesOrder::getOrderDate, start)
                .lt(SalSalesOrder::getOrderDate, end)
                .ge(SalSalesOrder::getStatus, 2).list();
        if (orders.isEmpty()) {
            return BigDecimal.ZERO;
        }
        Set<Long> orderIds = orders.stream().map(SalSalesOrder::getId).collect(Collectors.toSet());
        List<SalSalesOrderItem> items = salesOrderItemService.lambdaQuery()
                .in(SalSalesOrderItem::getOrderId, orderIds).list();
        BigDecimal salesAmount = items.stream()
                .map(SalSalesOrderItem::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal costAmount = items.stream()
                .map(item -> {
                    if (item.getProductId() == null || item.getQuantity() == null) {
                        return BigDecimal.ZERO;
                    }
                    InvInventory inv = inventoryService.lambdaQuery()
                            .eq(InvInventory::getProductId, item.getProductId())
                            .one();
                    if (inv != null && inv.getCostPrice() != null) {
                        return inv.getCostPrice().multiply(item.getQuantity());
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return salesAmount.subtract(costAmount);
    }
}
