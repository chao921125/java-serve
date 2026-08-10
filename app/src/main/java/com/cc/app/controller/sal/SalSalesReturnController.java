package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.sal.SalesReturnQueryDTO;
import com.cc.core.entity.sal.SalSalesReturn;
import com.cc.core.entity.sal.SalSalesReturnItem;
import com.cc.core.service.sal.SalSalesReturnItemService;
import com.cc.core.service.sal.SalSalesReturnService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售退货单控制器
 */
@RestController
@RequestMapping("/api/v1/sales-returns")
@RequiredArgsConstructor
public class SalSalesReturnController {

    private final SalSalesReturnService salesReturnService;
    private final SalSalesReturnItemService salesReturnItemService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<SalSalesReturn>> list(SalesReturnQueryDTO query) {
        return R.ok(salesReturnService.page(query));
    }

    /**
     * 详情（含明细 items）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        SalSalesReturn returnOrder = salesReturnService.getById(id);
        if (returnOrder == null) {
            return R.fail("销售退货单不存在");
        }
        List<SalSalesReturnItem> items = salesReturnService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("return", returnOrder);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody SalSalesReturn returnOrder) {
        salesReturnService.save(returnOrder);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        SalSalesReturn returnOrder = salesReturnService.getById(id);
        if (returnOrder == null) {
            return R.fail("销售退货单不存在");
        }
        if (returnOrder.getStatus() != 0) {
            return R.fail("只有草稿状态的退货单才能删除");
        }
        // 删除主表和明细
        salesReturnItemService.remove(
                new LambdaQueryWrapper<SalSalesReturnItem>()
                        .eq(SalSalesReturnItem::getReturnId, id)
        );
        return salesReturnService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核（核心：库存回库、写库存流水、冲减应收账款）
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        salesReturnService.approve(id);
        return R.ok();
    }
}
