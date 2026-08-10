package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.bas.BasWarehouse;
import com.cc.core.service.bas.BasWarehouseService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 仓库控制器
 */
@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
public class BasWarehouseController {

    private final BasWarehouseService warehouseService;

    /**
     * 分页列表
     */
    @GetMapping
    public R<Page<BasWarehouse>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        Page<BasWarehouse> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<BasWarehouse> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(BasWarehouse::getName, name);
        }
        if (status != null) {
            wrapper.eq(BasWarehouse::getStatus, status);
        }
        wrapper.orderByDesc(BasWarehouse::getCreateTime);
        return R.ok(warehouseService.page(p, wrapper));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<BasWarehouse> detail(@PathVariable Long id) {
        return R.ok(warehouseService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody BasWarehouse entity) {
        warehouseService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody BasWarehouse entity) {
        entity.setId(id);
        warehouseService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return warehouseService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
