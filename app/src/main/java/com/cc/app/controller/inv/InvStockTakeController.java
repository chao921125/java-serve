package com.cc.app.controller.inv;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.inv.StockTakeQueryDTO;
import com.cc.core.entity.inv.InvStockTake;
import com.cc.core.entity.inv.InvStockTakeItem;
import com.cc.core.service.inv.InvStockTakeService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 盘点单控制器
 */
@RestController
@RequestMapping("/api/v1/stock-takes")
@RequiredArgsConstructor
public class InvStockTakeController {

    private final InvStockTakeService stockTakeService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<InvStockTake>> list(StockTakeQueryDTO query) {
        return R.ok(stockTakeService.page(query));
    }

    /**
     * 详情（含明细）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        InvStockTake stockTake = stockTakeService.getById(id);
        if (stockTake == null) {
            return R.fail("盘点单不存在");
        }
        List<InvStockTakeItem> items = stockTakeService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("stockTake", stockTake);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody InvStockTake stockTake) {
        stockTake.setStatus(0);
        stockTakeService.save(stockTake);
        return R.ok();
    }

    /**
     * 删除（仅草稿状态）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        InvStockTake stockTake = stockTakeService.getById(id);
        if (stockTake == null) {
            return R.fail("盘点单不存在");
        }
        if (stockTake.getStatus() != 0) {
            return R.fail("只有草稿状态的盘点单才能删除");
        }
        return stockTakeService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        stockTakeService.approve(id);
        return R.ok();
    }
}
