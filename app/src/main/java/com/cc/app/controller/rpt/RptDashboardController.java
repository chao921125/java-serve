package com.cc.app.controller.rpt;

import com.cc.core.service.inv.InvInventoryService;
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

/**
 * 经营看板/驾驶舱控制器
 * 提供首页数据大屏所需的聚合统计数据
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class RptDashboardController {

    private final SalSalesOrderService salesOrderService;
    private final InvInventoryService inventoryService;

    /**
     * 首页数据总览
     */
    @GetMapping("/summary")
    public R<Map<String, Object>> summary() {
        Map<String, Object> result = new LinkedHashMap<>();
        // 查询今日销售订单
        LocalDate today = LocalDate.now();
        long todayOrderCount = salesOrderService.lambdaQuery()
                .ge(com.cc.core.entity.sal.SalSalesOrder::getOrderDate, today).count();
        long monthOrderCount = salesOrderService.lambdaQuery()
                .ge(com.cc.core.entity.sal.SalSalesOrder::getOrderDate, today.withDayOfMonth(1)).count();
        // 库存SKU数量
        long inventorySkuCount = inventoryService.lambdaQuery()
                .gt(com.cc.core.entity.inv.InvInventory::getQuantity, 0).count();
        result.put("todaySales", todayOrderCount);
        result.put("todayProfit", 0);
        result.put("monthSales", monthOrderCount);
        result.put("monthProfit", 0);
        result.put("inventoryValue", 0);
        result.put("receivableAmount", 0);
        result.put("payableAmount", 0);
        result.put("lowStockCount", 0);
        result.put("expiryAlertCount", 0);
        result.put("pendingOrderCount", salesOrderService.lambdaQuery()
                .eq(com.cc.core.entity.sal.SalSalesOrder::getStatus, 1).count());
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
            long count = salesOrderService.lambdaQuery()
                    .ge(com.cc.core.entity.sal.SalSalesOrder::getOrderDate, date)
                    .lt(com.cc.core.entity.sal.SalSalesOrder::getOrderDate, date.plusDays(1))
                    .count();
            Map<String, Object> point = new LinkedHashMap<>();
            point.put("date", date.toString());
            point.put("count", count);
            trend.add(point);
        }
        return R.ok(trend);
    }

    /**
     * 热销商品 TOP10
     */
    @GetMapping("/top-products")
    public R<List<Map<String, Object>>> topProducts() {
        return R.ok(List.of());
    }

    /**
     * 客户贡献 TOP10
     */
    @GetMapping("/top-customers")
    public R<List<Map<String, Object>>> topCustomers() {
        return R.ok(List.of());
    }

    /**
     * 库存预警汇总
     */
    @GetMapping("/stock-warning")
    public R<Map<String, Object>> stockWarning() {
        return R.ok(Map.of(
                "lowStockCount", 0,
                "highStockCount", 0,
                "expiryCount", 0
        ));
    }

    /**
     * 应收预警汇总
     */
    @GetMapping("/receivable-warning")
    public R<Map<String, Object>> receivableWarning() {
        return R.ok(Map.of(
                "overdueCount", 0,
                "overdueAmount", BigDecimal.ZERO
        ));
    }
}
