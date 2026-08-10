package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.dto.bas.SupplierSaveDTO;
import com.cc.core.entity.bas.BasSupplier;
import com.cc.core.service.bas.BasSupplierService;
import com.cc.framework.base.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 供应商控制器
 */
@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class BasSupplierController {

    private final BasSupplierService supplierService;

    /**
     * 分页列表
     */
    @GetMapping
    public R<Page<BasSupplier>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        Page<BasSupplier> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<BasSupplier> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(BasSupplier::getName, name);
        }
        if (status != null) {
            wrapper.eq(BasSupplier::getStatus, status);
        }
        wrapper.orderByDesc(BasSupplier::getCreateTime);
        return R.ok(supplierService.page(p, wrapper));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<BasSupplier> detail(@PathVariable Long id) {
        return R.ok(supplierService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@Valid @RequestBody SupplierSaveDTO dto) {
        BasSupplier entity = new BasSupplier();
        BeanUtils.copyProperties(dto, entity);
        supplierService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody SupplierSaveDTO dto) {
        BasSupplier entity = new BasSupplier();
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);
        supplierService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return supplierService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
