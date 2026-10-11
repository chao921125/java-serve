// AI生成
package com.cc.app.controller.portal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.fin.FinInvoice;
import com.cc.core.entity.fin.FinReceivable;
import com.cc.core.entity.sal.SalCustomerStatement;
import com.cc.core.entity.sal.SalSalesDelivery;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.service.fin.FinInvoiceService;
import com.cc.core.service.fin.FinReceivableService;
import com.cc.core.service.sal.SalCustomerStatementService;
import com.cc.core.service.sal.SalSalesDeliveryService;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 客户门户控制器
 */
@RestController
@RequestMapping("/api/portal/customer")
@RequiredArgsConstructor
public class PortalCustomerController {

    private final SalSalesOrderService salesOrderService;
    private final SalSalesDeliveryService salesDeliveryService;
    private final SalCustomerStatementService customerStatementService;
    private final FinInvoiceService invoiceService;
    private final FinReceivableService receivableService;

    /** 查看我的订单列表 */
    @GetMapping("/orders")
    public R<?> orders(
            @RequestParam Long customerId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SalSalesOrder> p = new Page<>(page, pageSize);
        IPage<SalSalesOrder> result = salesOrderService.lambdaQuery()
                .eq(SalSalesOrder::getCustomerId, customerId)
                .orderByDesc(SalSalesOrder::getCreateTime).page(p);
        return R.ok(result);
    }

    /** 订单详情 */
    @GetMapping("/orders/{id}")
    public R<?> orderDetail(@PathVariable Long id) {
        return R.ok(salesOrderService.getById(id));
    }

    /** 发货记录 */
    @GetMapping("/deliveries")
    public R<?> deliveries(@RequestParam Long customerId) {
        List<SalSalesDelivery> list = salesDeliveryService.lambdaQuery()
                .eq(SalSalesDelivery::getCustomerId, customerId)
                .orderByDesc(SalSalesDelivery::getDeliveryDate).list();
        return R.ok(list);
    }

    /** 对账单 */
    @GetMapping("/statements")
    public R<?> statements(@RequestParam Long customerId) {
        List<SalCustomerStatement> list = customerStatementService.lambdaQuery()
                .eq(SalCustomerStatement::getCustomerId, customerId)
                .orderByDesc(SalCustomerStatement::getCreateTime).list();
        return R.ok(list);
    }

    /** 发票记录 */
    @GetMapping("/invoices")
    public R<?> invoices(@RequestParam Long customerId) {
        List<FinInvoice> list = invoiceService.lambdaQuery()
                .eq(FinInvoice::getCounterpartyId, customerId)
                .eq(FinInvoice::getInvoiceType, "SALES_OUT")
                .orderByDesc(FinInvoice::getInvoiceDate).list();
        return R.ok(list);
    }

    /** 应收账款 */
    @GetMapping("/receivable")
    public R<?> receivable(@RequestParam Long customerId) {
        List<FinReceivable> list = receivableService.lambdaQuery()
                .eq(FinReceivable::getCustomerId, customerId)
                .lt(FinReceivable::getStatus, 2).list();
        BigDecimal totalBalance = list.stream()
                .map(FinReceivable::getBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return R.ok(java.util.Map.of(
                "list", list,
                "totalBalance", totalBalance
        ));
    }
}
