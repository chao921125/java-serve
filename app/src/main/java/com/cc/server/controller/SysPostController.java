package com.cc.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.SysPost;
import com.cc.core.service.SysPostService;
import com.cc.framework.annotation.Log;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/post")
@RequiredArgsConstructor
public class SysPostController {

    private final SysPostService postService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:post:list')")
    public R<Page<SysPost>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {
        Page<SysPost> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(SysPost::getName, name);
        }
        wrapper.orderByAsc(SysPost::getSort);
        return R.ok(postService.page(page, wrapper));
    }

    /**
     * 查询所有正常岗位
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:post:list')")
    public R<List<SysPost>> list() {
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPost::getStatus, 0);
        wrapper.orderByAsc(SysPost::getSort);
        return R.ok(postService.list(wrapper));
    }

    /**
     * 查询详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:post:query')")
    public R<SysPost> getInfo(@PathVariable Long id) {
        SysPost post = postService.getById(id);
        return post != null ? R.ok(post) : R.fail("岗位不存在");
    }

    /**
     * 新增
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:post:add')")
    @Log(title = "岗位管理", businessType = com.cc.core.enums.BusinessType.INSERT)
    public R<Void> add(@RequestBody SysPost post) {
        return postService.save(post) ? R.ok() : R.fail("新增失败");
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:post:edit')")
    @Log(title = "岗位管理", businessType = com.cc.core.enums.BusinessType.UPDATE)
    public R<Void> edit(@RequestBody SysPost post) {
        return postService.updateById(post) ? R.ok() : R.fail("修改失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:post:remove')")
    @Log(title = "岗位管理", businessType = com.cc.core.enums.BusinessType.DELETE)
    public R<Void> remove(@PathVariable Long id) {
        return postService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
