package com.cc.app.controller.portal;

import com.cc.core.entity.pur.PurPurchaseOrder;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.service.pur.PurPurchaseOrderService;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 客户门户控制器
 */
@RestController
@RequestMapping("/api/portal/customer")
@RequiredArgsConstructor
public class PortalCustomerController {

    private final SalSalesOrderService salesOrderService;
    private final PurPurchaseOrderService purchaseOrderService;

    /** 查看我的订单列表 */
    @GetMapping("/orders")
    public R<?> orders(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return R.ok();
    }

    /** 订单详情 */
    @GetMapping("/orders/{id}")
    public R<?> orderDetail(@PathVariable Long id) {
        return R.ok(salesOrderService.getById(id));
    }

    /** 发货记录 */
    @GetMapping("/deliveries")
    public R<?> deliveries() {
        return R.ok();
    }

    /** 对账单 */
    @GetMapping("/statements")
    public R<?> statements() {
        return R.ok();
    }

    /** 发票记录 */
    @GetMapping("/invoices")
    public R<?> invoices() {
        return R.ok();
    }

    /** 应收账款 */
    @GetMapping("/receivable")
    public R<?> receivable() {
        return R.ok();
    }
}
