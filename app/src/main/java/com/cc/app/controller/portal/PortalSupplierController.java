// AI生成
package com.cc.app.controller.portal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.fin.FinInvoice;
import com.cc.core.entity.fin.FinPayable;
import com.cc.core.entity.pur.PurPurchaseOrder;
import com.cc.core.service.fin.FinInvoiceService;
import com.cc.core.service.fin.FinPayableService;
import com.cc.core.service.pur.PurPurchaseOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 供应商门户控制器
 */
@RestController
@RequestMapping("/api/portal/supplier")
@RequiredArgsConstructor
public class PortalSupplierController {

    private final PurPurchaseOrderService purchaseOrderService;
    private final FinInvoiceService invoiceService;
    private final FinPayableService payableService;

    /** 查看采购订单 */
    @GetMapping("/orders")
    public R<?> orders(
            @RequestParam Long supplierId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PurPurchaseOrder> p = new Page<>(page, pageSize);
        IPage<PurPurchaseOrder> result = purchaseOrderService.lambdaQuery()
                .eq(PurPurchaseOrder::getSupplierId, supplierId)
                .orderByDesc(PurPurchaseOrder::getCreateTime).page(p);
        return R.ok(result);
    }

    /** 确认订单 */
    @PostMapping("/orders/{id}/confirm")
    public R<Void> confirmOrder(@PathVariable Long id) {
        PurPurchaseOrder order = purchaseOrderService.getById(id);
        if (order == null) {
            throw new RuntimeException("采购订单不存在");
        }
        if (order.getStatus() < 2) {
            order.setStatus(2);
            purchaseOrderService.updateById(order);
        }
        return R.ok();
    }

    /** 对账单 */
    @GetMapping("/statements")
    public R<?> statements(@RequestParam Long supplierId) {
        List<FinPayable> list = payableService.lambdaQuery()
                .eq(FinPayable::getSupplierId, supplierId)
                .orderByDesc(FinPayable::getCreateTime).list();
        return R.ok(list);
    }

    /** 发票记录 */
    @GetMapping("/invoices")
    public R<?> invoices(@RequestParam Long supplierId) {
        List<FinInvoice> list = invoiceService.lambdaQuery()
                .eq(FinInvoice::getCounterpartyId, supplierId)
                .eq(FinInvoice::getInvoiceType, "PURCHASE_IN")
                .orderByDesc(FinInvoice::getInvoiceDate).list();
        return R.ok(list);
    }

    /** 应付账款 */
    @GetMapping("/payable")
    public R<?> payable(@RequestParam Long supplierId) {
        List<FinPayable> list = payableService.lambdaQuery()
                .eq(FinPayable::getSupplierId, supplierId)
                .lt(FinPayable::getStatus, 2).list();
        BigDecimal totalBalance = list.stream()
                .map(FinPayable::getBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return R.ok(java.util.Map.of(
                "list", list,
                "totalBalance", totalBalance
        ));
    }
}
