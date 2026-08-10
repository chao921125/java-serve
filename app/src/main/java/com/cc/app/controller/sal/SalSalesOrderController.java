package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.sal.SalesOrderQueryDTO;
import com.cc.core.dto.sal.SalesOrderSaveDTO;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.entity.sal.SalSalesOrderItem;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.framework.base.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售订单控制器
 */
@RestController
@RequestMapping("/api/v1/sales-orders")
@RequiredArgsConstructor
public class SalSalesOrderController {

    private final SalSalesOrderService salesOrderService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<SalSalesOrder>> list(SalesOrderQueryDTO query) {
        return R.ok(salesOrderService.page(query));
    }

    /**
     * 详情（含明细 items）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        SalSalesOrder order = salesOrderService.getById(id);
        if (order == null) {
            return R.fail("销售订单不存在");
        }
        List<SalSalesOrderItem> items = salesOrderService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@Valid @RequestBody SalesOrderSaveDTO dto) {
        salesOrderService.create(dto);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody SalesOrderSaveDTO dto) {
        salesOrderService.update(id, dto);
        return R.ok();
    }

    /**
     * 删除（仅草稿状态）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        SalSalesOrder order = salesOrderService.getById(id);
        if (order == null) {
            return R.fail("销售订单不存在");
        }
        if (order.getStatus() != 0) {
            return R.fail("只有草稿状态的订单才能删除");
        }
        return salesOrderService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        salesOrderService.approve(id);
        return R.ok();
    }

    /**
     * 反审核
     */
    @PostMapping("/{id}/reject")
    public R<Void> reject(@PathVariable Long id) {
        salesOrderService.reject(id);
        return R.ok();
    }

    /**
     * 关闭
     */
    @PostMapping("/{id}/close")
    public R<Void> close(@PathVariable Long id) {
        salesOrderService.close(id);
        return R.ok();
    }

    /**
     * 挂单
     */
    @PostMapping("/{id}/suspend")
    public R<Void> suspend(@PathVariable Long id) {
        salesOrderService.suspend(id);
        return R.ok();
    }

    /**
     * 恢复挂单
     */
    @PostMapping("/{id}/resume")
    public R<Void> resume(@PathVariable Long id) {
        salesOrderService.resume(id);
        return R.ok();
    }

    /**
     * 挂单列表
     */
    @GetMapping("/suspended")
    public R<List<SalSalesOrder>> suspended() {
        return R.ok(salesOrderService.getSuspended());
    }
}
