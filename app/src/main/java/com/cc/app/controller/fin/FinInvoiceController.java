package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.fin.FinInvoice;
import com.cc.core.service.fin.FinInvoiceService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * FinInvoice 控制器
 */
@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class FinInvoiceController {

    private final FinInvoiceService service;

    @GetMapping
    public R<Page<FinInvoice>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<FinInvoice> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<FinInvoice> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(FinInvoice::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<FinInvoice> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody FinInvoice entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody FinInvoice entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/verify")
    public com.cc.framework.base.R<Void> verify(@PathVariable Long id) {
        service.verify(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/red-rush")
    public com.cc.framework.base.R<Void> redRush(@PathVariable Long id) {
        service.redRush(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/cancel")
    public com.cc.framework.base.R<Void> cancel(@PathVariable Long id) {
        service.cancel(id);
        return com.cc.framework.base.R.ok();
    }

    @GetMapping("/unbilled")
    public com.cc.framework.base.R<java.util.List<FinInvoice>> unbilled() {
        return com.cc.framework.base.R.ok(service.getUnbilled());
    }

}
