package com.cc.app.controller.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.inv.InvStockAging;
import com.cc.core.service.inv.InvStockAgingService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * InvStockAging 控制器
 */
@RestController
@RequestMapping("/api/v1/stock-aging")
@RequiredArgsConstructor
public class InvStockAgingController {

    private final InvStockAgingService service;

    @GetMapping
    public R<Page<InvStockAging>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<InvStockAging> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<InvStockAging> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(InvStockAging::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<InvStockAging> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody InvStockAging entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody InvStockAging entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/snapshot")
    public com.cc.framework.base.R<Void> generateSnapshot() {
        service.generateSnapshot();
        return com.cc.framework.base.R.ok();
    }

    @GetMapping("/slow-moving")
    public com.cc.framework.base.R<Page<InvStockAging>> slowMoving(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<InvStockAging> p = new Page<>(page, pageSize);
        return com.cc.framework.base.R.ok(service.getSlowMoving(p));
    }

    @GetMapping("/turnover-rate")
    public com.cc.framework.base.R<java.util.List<java.util.Map<String, Object>>> turnoverRate(
            @RequestParam(defaultValue = "30") Integer days) {
        return com.cc.framework.base.R.ok(service.getTurnoverRate(days));
    }

}
