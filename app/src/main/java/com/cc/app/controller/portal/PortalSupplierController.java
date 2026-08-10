package com.cc.app.controller.portal;

import com.cc.core.service.pur.PurPurchaseOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 供应商门户控制器
 */
@RestController
@RequestMapping("/api/portal/supplier")
@RequiredArgsConstructor
public class PortalSupplierController {

    private final PurPurchaseOrderService purchaseOrderService;

    /** 查看采购订单 */
    @GetMapping("/orders")
    public R<?> orders(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return R.ok();
    }

    /** 确认订单 */
    @PostMapping("/orders/{id}/confirm")
    public R<Void> confirmOrder(@PathVariable Long id) {
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

    /** 应付账款 */
    @GetMapping("/payable")
    public R<?> payable() {
        return R.ok();
    }
}
