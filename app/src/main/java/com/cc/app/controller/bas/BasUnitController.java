package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.bas.BasUnit;
import com.cc.core.service.bas.BasUnitService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 计量单位控制器
 */
@RestController
@RequestMapping("/api/v1/units")
@RequiredArgsConstructor
public class BasUnitController {

    private final BasUnitService unitService;

    /**
     * 分页列表
     */
    @GetMapping
    public R<Page<BasUnit>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        Page<BasUnit> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<BasUnit> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(BasUnit::getName, name);
        }
        if (status != null) {
            wrapper.eq(BasUnit::getStatus, status);
        }
        wrapper.orderByDesc(BasUnit::getCreateTime);
        return R.ok(unitService.page(p, wrapper));
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody BasUnit entity) {
        unitService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody BasUnit entity) {
        entity.setId(id);
        unitService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return unitService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
