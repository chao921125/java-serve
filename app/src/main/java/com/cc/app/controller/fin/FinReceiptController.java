package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.fin.ReceiptQueryDTO;
import com.cc.core.entity.fin.FinReceipt;
import com.cc.core.entity.fin.FinReceiptItem;
import com.cc.core.service.fin.FinReceiptService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收款单控制器
 */
@RestController
@RequestMapping("/api/v1/receipts")
@RequiredArgsConstructor
public class FinReceiptController {

    private final FinReceiptService receiptService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<FinReceipt>> list(ReceiptQueryDTO query) {
        return R.ok(receiptService.page(query));
    }

    /**
     * 详情（含明细）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        FinReceipt receipt = receiptService.getById(id);
        if (receipt == null) {
            return R.fail("收款单不存在");
        }
        List<FinReceiptItem> items = receiptService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("receipt", receipt);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody FinReceipt receipt) {
        receipt.setStatus(0);
        receiptService.save(receipt);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody FinReceipt receipt) {
        receipt.setId(id);
        receiptService.updateById(receipt);
        return R.ok();
    }

    /**
     * 删除（仅草稿状态）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        FinReceipt receipt = receiptService.getById(id);
        if (receipt == null) {
            return R.fail("收款单不存在");
        }
        if (receipt.getStatus() != 0) {
            return R.fail("只有草稿状态的收款单才能删除");
        }
        return receiptService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        receiptService.approve(id);
        return R.ok();
    }
}
