package com.cc.app.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sys.SysDataBackup;
import com.cc.core.service.sys.SysDataBackupService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SysDataBackup 控制器
 */
@RestController
@RequestMapping("/api/sys/backups")
@RequiredArgsConstructor
public class SysDataBackupController {

    private final SysDataBackupService service;

    @GetMapping
    public R<Page<SysDataBackup>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysDataBackup> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SysDataBackup> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysDataBackup::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SysDataBackup> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SysDataBackup entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysDataBackup entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/restore")
    public R<Void> restore(@PathVariable Long id) {
        service.restore(id);
        return R.ok();
    }

    @GetMapping("/{id}/download")
    public R<String> download(@PathVariable Long id) {
        String downloadUrl = service.download(id);
        return R.ok(downloadUrl);
    }
}
