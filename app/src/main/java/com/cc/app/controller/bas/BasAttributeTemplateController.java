package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.bas.BasAttributeTemplate;
import com.cc.core.service.bas.BasAttributeTemplateService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性模板控制器
 */
@RestController
@RequestMapping("/api/v1/attribute-templates")
@RequiredArgsConstructor
public class BasAttributeTemplateController {

    private final BasAttributeTemplateService templateService;

    /**
     * 分页列表
     */
    @GetMapping
    public R<Page<BasAttributeTemplate>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId) {
        Page<BasAttributeTemplate> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<BasAttributeTemplate> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(BasAttributeTemplate::getName, name);
        }
        if (categoryId != null) {
            wrapper.eq(BasAttributeTemplate::getCategoryId, categoryId);
        }
        wrapper.orderByAsc(BasAttributeTemplate::getSortOrder);
        return R.ok(templateService.page(p, wrapper));
    }

    /**
     * 全部列表（下拉选择用）
     */
    @GetMapping("/all")
    public R<List<BasAttributeTemplate>> all() {
        LambdaQueryWrapper<BasAttributeTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BasAttributeTemplate::getIsEnabled, 1);
        wrapper.orderByAsc(BasAttributeTemplate::getSortOrder);
        return R.ok(templateService.list(wrapper));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<BasAttributeTemplate> detail(@PathVariable Long id) {
        return R.ok(templateService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody BasAttributeTemplate entity) {
        entity.setIsEnabled(1);
        templateService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody BasAttributeTemplate entity) {
        entity.setId(id);
        templateService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return templateService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 启用/停用
     */
    @PutMapping("/{id}/toggle")
    public R<Void> toggle(@PathVariable Long id) {
        templateService.toggleEnabled(id);
        return R.ok();
    }
}
