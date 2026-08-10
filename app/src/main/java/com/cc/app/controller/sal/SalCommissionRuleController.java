package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sal.SalCommissionRule;
import com.cc.core.service.sal.SalCommissionRuleService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SalCommissionRule 控制器
 */
@RestController
@RequestMapping("/api/v1/commission-rules")
@RequiredArgsConstructor
public class SalCommissionRuleController {

    private final SalCommissionRuleService service;

    @GetMapping
    public R<Page<SalCommissionRule>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SalCommissionRule> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SalCommissionRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SalCommissionRule::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SalCommissionRule> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SalCommissionRule entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SalCommissionRule entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PutMapping("/{id}/toggle")
    public com.cc.framework.base.R<Void> toggleEnabled(@PathVariable Long id) {
        service.toggleEnabled(id);
        return com.cc.framework.base.R.ok();
    }

}
