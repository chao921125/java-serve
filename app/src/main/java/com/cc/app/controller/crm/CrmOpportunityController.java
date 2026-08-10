package com.cc.app.controller.crm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.crm.CrmOpportunity;
import com.cc.core.service.crm.CrmOpportunityService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * CrmOpportunity 控制器
 */
@RestController
@RequestMapping("/api/v1/crm/opportunities")
@RequiredArgsConstructor
public class CrmOpportunityController {

    private final CrmOpportunityService service;

    @GetMapping
    public R<Page<CrmOpportunity>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<CrmOpportunity> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<CrmOpportunity> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(CrmOpportunity::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<CrmOpportunity> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody CrmOpportunity entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody CrmOpportunity entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
