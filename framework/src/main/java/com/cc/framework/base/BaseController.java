package com.cc.framework.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.base.BaseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 通用 CRUD 基础控制器
 *
 * @param <T> 实体类型
 * @param <S> Service 类型
 */
public abstract class BaseController<T extends BaseEntity, S extends IService<T>> {

    @Autowired
    protected S baseService;

    /**
     * 根据 ID 查询
     */
    @GetMapping("/{id}")
    public R<T> getById(@PathVariable Long id) {
        T entity = baseService.getById(id);
        return entity != null ? R.ok(entity) : R.fail("数据不存在");
    }

    /**
     * 新增
     */
    @PostMapping
    public R<T> add(@Valid @RequestBody T entity) {
        boolean saved = baseService.save(entity);
        return saved ? R.ok(entity) : R.fail("新增失败");
    }

    /**
     * 修改
     */
    @PutMapping
    public R<T> update(@Valid @RequestBody T entity) {
        boolean updated = baseService.updateById(entity);
        return updated ? R.ok(entity) : R.fail("修改失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean removed = baseService.removeById(id);
        return removed ? R.ok() : R.fail("删除失败");
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public R<Void> deleteBatch(@RequestBody List<Long> ids) {
        boolean removed = baseService.removeByIds(ids);
        return removed ? R.ok() : R.fail("批量删除失败");
    }

    /**
     * 查询列表
     */
    @GetMapping("/list")
    public R<List<T>> list() {
        List<T> list = baseService.list();
        return R.ok(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public R<PageResult<T>> page(PageQuery pageQuery) {
        Page<T> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        // 排序
        if (pageQuery.getOrderByColumn() != null && !pageQuery.getOrderByColumn().isEmpty()) {
            boolean isAsc = "asc".equalsIgnoreCase(pageQuery.getIsAsc());
            wrapper.orderBy(true, isAsc, pageQuery.getOrderByColumn());
        } else {
            wrapper.orderByDesc("create_time");
        }

        IPage<T> result = baseService.page(page, wrapper);
        return R.ok(PageResult.of(result.getTotal(), result.getRecords(),
                pageQuery.getPageNum(), pageQuery.getPageSize()));
    }
}
