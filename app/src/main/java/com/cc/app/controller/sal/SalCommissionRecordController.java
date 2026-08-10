package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sal.SalCommissionRecord;
import com.cc.core.service.sal.SalCommissionRecordService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SalCommissionRecord 控制器
 */
@RestController
@RequestMapping("/api/v1/commission-records")
@RequiredArgsConstructor
public class SalCommissionRecordController {

    private final SalCommissionRecordService service;

    @GetMapping
    public R<Page<SalCommissionRecord>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SalCommissionRecord> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SalCommissionRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SalCommissionRecord::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SalCommissionRecord> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SalCommissionRecord entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SalCommissionRecord entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/calculate")
    public com.cc.framework.base.R<Void> calculate(@RequestParam String period) {
        service.calculate(period);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/pay")
    public com.cc.framework.base.R<Void> pay(@PathVariable Long id) {
        service.pay(id);
        return com.cc.framework.base.R.ok();
    }

}
