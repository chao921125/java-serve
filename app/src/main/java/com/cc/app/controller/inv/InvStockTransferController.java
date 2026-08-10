package com.cc.app.controller.inv;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.inv.StockTransferQueryDTO;
import com.cc.core.entity.inv.InvStockTransfer;
import com.cc.core.entity.inv.InvStockTransferItem;
import com.cc.core.service.inv.InvStockTransferService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调拨单控制器
 */
@RestController
@RequestMapping("/api/v1/stock-transfers")
@RequiredArgsConstructor
public class InvStockTransferController {

    private final InvStockTransferService stockTransferService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<InvStockTransfer>> list(StockTransferQueryDTO query) {
        return R.ok(stockTransferService.page(query));
    }

    /**
     * 详情（含明细）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        InvStockTransfer transfer = stockTransferService.getById(id);
        if (transfer == null) {
            return R.fail("调拨单不存在");
        }
        List<InvStockTransferItem> items = stockTransferService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("transfer", transfer);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody InvStockTransfer transfer) {
        transfer.setStatus(0);
        stockTransferService.save(transfer);
        return R.ok();
    }

    /**
     * 删除（仅草稿状态）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        InvStockTransfer transfer = stockTransferService.getById(id);
        if (transfer == null) {
            return R.fail("调拨单不存在");
        }
        if (transfer.getStatus() != 0) {
            return R.fail("只有草稿状态的调拨单才能删除");
        }
        return stockTransferService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        stockTransferService.approve(id);
        return R.ok();
    }
}
