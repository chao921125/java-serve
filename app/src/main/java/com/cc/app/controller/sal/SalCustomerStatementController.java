package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sal.SalCustomerStatement;
import com.cc.core.service.sal.SalCustomerStatementService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SalCustomerStatement 控制器
 */
@RestController
@RequestMapping("/api/v1/customer-statements")
@RequiredArgsConstructor
public class SalCustomerStatementController {

    private final SalCustomerStatementService service;

    @GetMapping
    public R<Page<SalCustomerStatement>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SalCustomerStatement> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SalCustomerStatement> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SalCustomerStatement::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SalCustomerStatement> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SalCustomerStatement entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SalCustomerStatement entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/generate")
    public com.cc.framework.base.R<Void> generate(@RequestParam Long customerId,
            @RequestParam java.time.LocalDate startDate,
            @RequestParam java.time.LocalDate endDate) {
        service.generate(customerId, startDate, endDate);
        return com.cc.framework.base.R.ok();
    }

    @PutMapping("/{id}/confirm")
    public com.cc.framework.base.R<Void> confirm(@PathVariable Long id) {
        service.confirm(id);
        return com.cc.framework.base.R.ok();
    }

    @PutMapping("/{id}/dispute")
    public com.cc.framework.base.R<Void> dispute(@PathVariable Long id, @RequestParam String reason) {
        service.dispute(id, reason);
        return com.cc.framework.base.R.ok();
    }

}
