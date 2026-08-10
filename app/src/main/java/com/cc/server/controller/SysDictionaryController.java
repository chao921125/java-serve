package com.cc.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.SysDictionary;
import com.cc.core.service.SysDictionaryService;
import com.cc.framework.annotation.Log;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 字典管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/dict")
@RequiredArgsConstructor
public class SysDictionaryController {

    private final SysDictionaryService dictService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:dict:list')")
    public R<Page<SysDictionary>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {
        Page<SysDictionary> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysDictionary> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(SysDictionary::getName, name);
        }
        wrapper.orderByDesc(SysDictionary::getCreateTime);
        return R.ok(dictService.page(page, wrapper));
    }

    /**
     * 查询所有正常字典
     */
    @GetMapping("/list")
    public R<?> list() {
        LambdaQueryWrapper<SysDictionary> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictionary::getStatus, 0);
        return R.ok(dictService.list(wrapper));
    }

    /**
     * 查询详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dict:query')")
    public R<SysDictionary> getInfo(@PathVariable Long id) {
        SysDictionary dict = dictService.getById(id);
        return dict != null ? R.ok(dict) : R.fail("字典不存在");
    }

    /**
     * 根据名称获取字典值
     */
    @GetMapping("/value/{name}")
    public R<String> getValue(@PathVariable String name) {
        return R.ok(dictService.getValueByName(name));
    }

    /**
     * 新增
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:dict:add')")
    @Log(title = "字典管理", businessType = com.cc.core.enums.BusinessType.INSERT)
    public R<Void> add(@RequestBody SysDictionary dict) {
        return dictService.save(dict) ? R.ok() : R.fail("新增失败");
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:dict:edit')")
    @Log(title = "字典管理", businessType = com.cc.core.enums.BusinessType.UPDATE)
    public R<Void> edit(@RequestBody SysDictionary dict) {
        return dictService.updateById(dict) ? R.ok() : R.fail("修改失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dict:remove')")
    @Log(title = "字典管理", businessType = com.cc.core.enums.BusinessType.DELETE)
    public R<Void> remove(@PathVariable Long id) {
        return dictService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
