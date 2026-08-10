package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.pur.PurExpenseAllocation;
import com.cc.core.service.pur.PurExpenseAllocationService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * PurExpenseAllocation 控制器
 */
@RestController
@RequestMapping("/api/v1/purchase-expense-allocations")
@RequiredArgsConstructor
public class PurExpenseAllocationController {

    private final PurExpenseAllocationService service;

    @GetMapping
    public R<Page<PurExpenseAllocation>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PurExpenseAllocation> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<PurExpenseAllocation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(PurExpenseAllocation::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<PurExpenseAllocation> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody PurExpenseAllocation entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody PurExpenseAllocation entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/allocate")
    public com.cc.framework.base.R<Void> allocate(@PathVariable Long id) {
        service.allocate(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/reverse")
    public com.cc.framework.base.R<Void> reverse(@PathVariable Long id) {
        service.reverse(id);
        return com.cc.framework.base.R.ok();
    }

}
