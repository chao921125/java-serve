package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sal.SalQuotation;
import com.cc.core.service.sal.SalQuotationService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SalQuotation 控制器
 */
@RestController
@RequestMapping("/api/v1/quotations")
@RequiredArgsConstructor
public class SalQuotationController {

    private final SalQuotationService service;

    @GetMapping
    public R<Page<SalQuotation>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SalQuotation> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SalQuotation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SalQuotation::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SalQuotation> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SalQuotation entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SalQuotation entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/issue")
    public com.cc.framework.base.R<Void> issue(@PathVariable Long id) {
        service.issue(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/confirm")
    public com.cc.framework.base.R<Void> confirm(@PathVariable Long id) {
        service.confirm(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/to-order")
    public com.cc.framework.base.R<Long> convertToOrder(@PathVariable Long id) {
        Long orderId = service.convertToOrder(id);
        return com.cc.framework.base.R.ok(orderId);
    }

    @PostMapping("/{id}/expire")
    public com.cc.framework.base.R<Void> expire(@PathVariable Long id) {
        service.expire(id);
        return com.cc.framework.base.R.ok();
    }

}
