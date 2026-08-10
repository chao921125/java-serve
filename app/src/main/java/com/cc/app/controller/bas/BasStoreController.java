package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.bas.BasStore;
import com.cc.core.service.bas.BasStoreService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * BasStore 控制器
 */
@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class BasStoreController {

    private final BasStoreService service;

    @GetMapping
    public R<Page<BasStore>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<BasStore> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<BasStore> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(BasStore::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<BasStore> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody BasStore entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody BasStore entity) {
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
