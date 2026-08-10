package com.cc.app.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sys.SysAttachment;
import com.cc.core.service.sys.SysAttachmentService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SysAttachment 控制器
 */
@RestController
@RequestMapping("/api/sys/files")
@RequiredArgsConstructor
public class SysAttachmentController {

    private final SysAttachmentService service;

    @GetMapping
    public R<Page<SysAttachment>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysAttachment> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SysAttachment> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysAttachment::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SysAttachment> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SysAttachment entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysAttachment entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
