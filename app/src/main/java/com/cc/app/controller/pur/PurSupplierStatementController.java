package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.pur.PurSupplierStatement;
import com.cc.core.service.pur.PurSupplierStatementService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * PurSupplierStatement 控制器
 */
@RestController
@RequestMapping("/api/v1/supplier-statements")
@RequiredArgsConstructor
public class PurSupplierStatementController {

    private final PurSupplierStatementService service;

    @GetMapping
    public R<Page<PurSupplierStatement>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PurSupplierStatement> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<PurSupplierStatement> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(PurSupplierStatement::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<PurSupplierStatement> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody PurSupplierStatement entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody PurSupplierStatement entity) {
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
    public com.cc.framework.base.R<Void> generate(@RequestParam Long supplierId,
            @RequestParam java.time.LocalDate startDate,
            @RequestParam java.time.LocalDate endDate) {
        service.generate(supplierId, startDate, endDate);
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
