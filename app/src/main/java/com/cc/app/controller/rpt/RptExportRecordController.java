package com.cc.app.controller.rpt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.rpt.RptExportRecord;
import com.cc.core.service.rpt.RptExportRecordService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * RptExportRecord 控制器
 */
@RestController
@RequestMapping("/api/v1/reports/export-records")
@RequiredArgsConstructor
public class RptExportRecordController {

    private final RptExportRecordService service;

    @GetMapping
    public R<Page<RptExportRecord>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<RptExportRecord> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<RptExportRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(RptExportRecord::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<RptExportRecord> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody RptExportRecord entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody RptExportRecord entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
