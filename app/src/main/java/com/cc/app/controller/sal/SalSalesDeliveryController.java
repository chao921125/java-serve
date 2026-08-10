package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.sal.SalesDeliveryQueryDTO;
import com.cc.core.entity.sal.SalSalesDelivery;
import com.cc.core.entity.sal.SalSalesDeliveryItem;
import com.cc.core.service.sal.SalSalesDeliveryItemService;
import com.cc.core.service.sal.SalSalesDeliveryService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售出库单控制器
 */
@RestController
@RequestMapping("/api/v1/sales-deliveries")
@RequiredArgsConstructor
public class SalSalesDeliveryController {

    private final SalSalesDeliveryService salesDeliveryService;
    private final SalSalesDeliveryItemService salesDeliveryItemService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<SalSalesDelivery>> list(SalesDeliveryQueryDTO query) {
        return R.ok(salesDeliveryService.page(query));
    }

    /**
     * 详情（含明细 items）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        SalSalesDelivery delivery = salesDeliveryService.getById(id);
        if (delivery == null) {
            return R.fail("销售出库单不存在");
        }
        List<SalSalesDeliveryItem> items = salesDeliveryService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("delivery", delivery);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody SalSalesDelivery delivery) {
        salesDeliveryService.save(delivery);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        SalSalesDelivery delivery = salesDeliveryService.getById(id);
        if (delivery == null) {
            return R.fail("销售出库单不存在");
        }
        if (delivery.getStatus() != 0) {
            return R.fail("只有草稿状态的出库单才能删除");
        }
        // 删除主表和明细
        salesDeliveryItemService.remove(
                new LambdaQueryWrapper<SalSalesDeliveryItem>()
                        .eq(SalSalesDeliveryItem::getDeliveryId, id)
        );
        return salesDeliveryService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核（核心：扣减库存+锁定数量、写库存流水、生成应收账款、更新订单已发货数量）
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        salesDeliveryService.approve(id);
        return R.ok();
    }
}
