package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cc.core.dto.bas.CategorySaveDTO;
import com.cc.core.entity.bas.BasProductCategory;
import com.cc.core.service.bas.BasProductCategoryService;
import com.cc.framework.base.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品分类控制器
 */
@RestController
@RequestMapping("/api/v1/product-categories")
@RequiredArgsConstructor
public class BasProductCategoryController {

    private final BasProductCategoryService categoryService;

    /**
     * 分类树
     */
    @GetMapping("/tree")
    public R<List<BasProductCategory>> tree() {
        LambdaQueryWrapper<BasProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(BasProductCategory::getSort);
        List<BasProductCategory> all = categoryService.list(wrapper);
        // 构建树
        Map<Long, List<BasProductCategory>> grouped = all.stream()
                .collect(Collectors.groupingBy(c -> c.getParentId() == null ? 0L : c.getParentId()));
        all.forEach(c -> {
            List<BasProductCategory> children = grouped.get(c.getId());
            // 子节点放入 params 字段（非表字段）便于返回
            if (children != null) {
                c.setParams(new java.util.HashMap<>());
                c.getParams().put("children", children);
            }
        });
        List<BasProductCategory> roots = grouped.getOrDefault(0L, new ArrayList<>());
        return R.ok(roots);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@Valid @RequestBody CategorySaveDTO dto) {
        BasProductCategory entity = new BasProductCategory();
        BeanUtils.copyProperties(dto, entity);
        categoryService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody CategorySaveDTO dto) {
        BasProductCategory entity = new BasProductCategory();
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);
        categoryService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除（逻辑删除）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return categoryService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
