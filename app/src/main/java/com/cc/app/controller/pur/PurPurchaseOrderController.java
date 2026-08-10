package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.pur.PurchaseOrderQueryDTO;
import com.cc.core.dto.pur.PurchaseOrderSaveDTO;
import com.cc.core.entity.pur.PurPurchaseOrder;
import com.cc.core.entity.pur.PurPurchaseOrderItem;
import com.cc.core.service.pur.PurPurchaseOrderService;
import com.cc.framework.base.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购订单控制器
 */
@RestController
@RequestMapping("/api/v1/purchase-orders")
@RequiredArgsConstructor
public class PurPurchaseOrderController {

    private final PurPurchaseOrderService purchaseOrderService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<PurPurchaseOrder>> list(PurchaseOrderQueryDTO query) {
        return R.ok(purchaseOrderService.page(query));
    }

    /**
     * 详情（含明细 items）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        PurPurchaseOrder order = purchaseOrderService.getById(id);
        if (order == null) {
            return R.fail("采购订单不存在");
        }
        List<PurPurchaseOrderItem> items = purchaseOrderService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@Valid @RequestBody PurchaseOrderSaveDTO dto) {
        purchaseOrderService.create(dto);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody PurchaseOrderSaveDTO dto) {
        purchaseOrderService.update(id, dto);
        return R.ok();
    }

    /**
     * 删除（仅草稿状态）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        PurPurchaseOrder order = purchaseOrderService.getById(id);
        if (order == null) {
            return R.fail("采购订单不存在");
        }
        if (order.getStatus() != 0) {
            return R.fail("只有草稿状态的订单才能删除");
        }
        return purchaseOrderService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        purchaseOrderService.approve(id);
        return R.ok();
    }

    /**
     * 反审核
     */
    @PostMapping("/{id}/reject")
    public R<Void> reject(@PathVariable Long id) {
        purchaseOrderService.reject(id);
        return R.ok();
    }

    /**
     * 关闭
     */
    @PostMapping("/{id}/close")
    public R<Void> close(@PathVariable Long id) {
        purchaseOrderService.close(id);
        return R.ok();
    }
}
