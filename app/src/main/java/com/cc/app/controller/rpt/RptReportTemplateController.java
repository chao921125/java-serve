package com.cc.app.controller.rpt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.rpt.RptReportTemplate;
import com.cc.core.service.rpt.RptReportTemplateService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * RptReportTemplate 控制器
 */
@RestController
@RequestMapping("/api/v1/report-templates")
@RequiredArgsConstructor
public class RptReportTemplateController {

    private final RptReportTemplateService service;

    @GetMapping
    public R<Page<RptReportTemplate>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<RptReportTemplate> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<RptReportTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(RptReportTemplate::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<RptReportTemplate> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody RptReportTemplate entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody RptReportTemplate entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
