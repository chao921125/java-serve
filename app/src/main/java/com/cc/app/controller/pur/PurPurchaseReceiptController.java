package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.pur.PurchaseReceiptQueryDTO;
import com.cc.core.entity.pur.PurPurchaseReceipt;
import com.cc.core.entity.pur.PurPurchaseReceiptItem;
import com.cc.core.service.pur.PurPurchaseReceiptItemService;
import com.cc.core.service.pur.PurPurchaseReceiptService;
import com.cc.framework.base.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购入库单控制器
 */
@RestController
@RequestMapping("/api/v1/purchase-receipts")
@RequiredArgsConstructor
public class PurPurchaseReceiptController {

    private final PurPurchaseReceiptService purchaseReceiptService;
    private final PurPurchaseReceiptItemService purchaseReceiptItemService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<PurPurchaseReceipt>> list(PurchaseReceiptQueryDTO query) {
        return R.ok(purchaseReceiptService.page(query));
    }

    /**
     * 详情（含明细 items）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        PurPurchaseReceipt receipt = purchaseReceiptService.getById(id);
        if (receipt == null) {
            return R.fail("采购入库单不存在");
        }
        List<PurPurchaseReceiptItem> items = purchaseReceiptService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("receipt", receipt);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody PurPurchaseReceipt receipt) {
        purchaseReceiptService.save(receipt);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        PurPurchaseReceipt receipt = purchaseReceiptService.getById(id);
        if (receipt == null) {
            return R.fail("采购入库单不存在");
        }
        if (receipt.getStatus() != 0) {
            return R.fail("只有草稿状态的入库单才能删除");
        }
        // 删除主表和明细
        purchaseReceiptItemService.remove(
                new LambdaQueryWrapper<PurPurchaseReceiptItem>()
                        .eq(PurPurchaseReceiptItem::getReceiptId, id)
        );
        return purchaseReceiptService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核（核心：更新库存 + 生成应付账款）
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        purchaseReceiptService.approve(id);
        return R.ok();
    }
}
