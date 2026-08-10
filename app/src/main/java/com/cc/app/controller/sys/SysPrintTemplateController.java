package com.cc.app.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sys.SysPrintTemplate;
import com.cc.core.service.sys.SysPrintTemplateService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SysPrintTemplate 控制器
 */
@RestController
@RequestMapping("/api/sys/print-templates")
@RequiredArgsConstructor
public class SysPrintTemplateController {

    private final SysPrintTemplateService service;

    @GetMapping
    public R<Page<SysPrintTemplate>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysPrintTemplate> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SysPrintTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysPrintTemplate::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SysPrintTemplate> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SysPrintTemplate entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysPrintTemplate entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/preview/{businessId}")
    public R<String> preview(@PathVariable Long id, @PathVariable Long businessId) {
        String content = service.preview(id, businessId);
        return R.ok(content);
    }

    @PostMapping("/{id}/set-default")
    public R<Void> setDefault(@PathVariable Long id) {
        service.setDefault(id);
        return R.ok();
    }
}
