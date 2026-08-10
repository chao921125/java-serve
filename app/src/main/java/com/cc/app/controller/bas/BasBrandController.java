package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.bas.BasBrand;
import com.cc.core.service.bas.BasBrandService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 品牌控制器
 */
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BasBrandController {

    private final BasBrandService brandService;

    /**
     * 分页列表
     */
    @GetMapping
    public R<Page<BasBrand>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        Page<BasBrand> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<BasBrand> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(BasBrand::getName, name);
        }
        if (status != null) {
            wrapper.eq(BasBrand::getStatus, status);
        }
        wrapper.orderByDesc(BasBrand::getCreateTime);
        return R.ok(brandService.page(p, wrapper));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<BasBrand> detail(@PathVariable Long id) {
        return R.ok(brandService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody BasBrand entity) {
        brandService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody BasBrand entity) {
        entity.setId(id);
        brandService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return brandService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
