package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.fin.FinExpenseAllocation;
import com.cc.core.service.fin.FinExpenseAllocationService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * FinExpenseAllocation 控制器
 */
@RestController
@RequestMapping("/api/v1/expense-allocations")
@RequiredArgsConstructor
public class FinExpenseAllocationController {

    private final FinExpenseAllocationService service;

    @GetMapping
    public R<Page<FinExpenseAllocation>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<FinExpenseAllocation> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<FinExpenseAllocation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(FinExpenseAllocation::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<FinExpenseAllocation> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody FinExpenseAllocation entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody FinExpenseAllocation entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
