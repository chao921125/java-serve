package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sal.SalExchangeOrder;
import com.cc.core.service.sal.SalExchangeOrderService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SalExchangeOrder 控制器
 */
@RestController
@RequestMapping("/api/v1/exchange-orders")
@RequiredArgsConstructor
public class SalExchangeOrderController {

    private final SalExchangeOrderService service;

    @GetMapping
    public R<Page<SalExchangeOrder>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SalExchangeOrder> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SalExchangeOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SalExchangeOrder::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SalExchangeOrder> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SalExchangeOrder entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SalExchangeOrder entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/approve")
    public com.cc.framework.base.R<Void> approve(@PathVariable Long id, @RequestParam Long approverId) {
        service.approve(id, approverId);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/complete")
    public com.cc.framework.base.R<Void> complete(@PathVariable Long id) {
        service.complete(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/calculate-difference")
    public com.cc.framework.base.R<Void> calculateDifference(@PathVariable Long id) {
        service.calculateDifference(id);
        return com.cc.framework.base.R.ok();
    }

}
