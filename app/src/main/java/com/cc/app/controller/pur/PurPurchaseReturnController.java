package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.pur.PurchaseReturnQueryDTO;
import com.cc.core.entity.pur.PurPurchaseReturn;
import com.cc.core.entity.pur.PurPurchaseReturnItem;
import com.cc.core.service.pur.PurPurchaseReturnItemService;
import com.cc.core.service.pur.PurPurchaseReturnService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购退货单控制器
 */
@RestController
@RequestMapping("/api/v1/purchase-returns")
@RequiredArgsConstructor
public class PurPurchaseReturnController {

    private final PurPurchaseReturnService purchaseReturnService;
    private final PurPurchaseReturnItemService purchaseReturnItemService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<PurPurchaseReturn>> list(PurchaseReturnQueryDTO query) {
        return R.ok(purchaseReturnService.page(query));
    }

    /**
     * 详情（含明细 items）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        PurPurchaseReturn returnOrder = purchaseReturnService.getById(id);
        if (returnOrder == null) {
            return R.fail("采购退货单不存在");
        }
        List<PurPurchaseReturnItem> items = purchaseReturnService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("returnOrder", returnOrder);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody PurPurchaseReturn returnOrder) {
        purchaseReturnService.save(returnOrder);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        PurPurchaseReturn returnOrder = purchaseReturnService.getById(id);
        if (returnOrder == null) {
            return R.fail("采购退货单不存在");
        }
        if (returnOrder.getStatus() != 0) {
            return R.fail("只有草稿状态的退货单才能删除");
        }
        purchaseReturnItemService.remove(
                new LambdaQueryWrapper<PurPurchaseReturnItem>()
                        .eq(PurPurchaseReturnItem::getReturnId, id)
        );
        return purchaseReturnService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        purchaseReturnService.approve(id);
        return R.ok();
    }
}
