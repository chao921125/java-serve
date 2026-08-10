package com.cc.app.controller.crm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.crm.CrmLead;
import com.cc.core.service.crm.CrmLeadService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * CrmLead 控制器
 */
@RestController
@RequestMapping("/api/v1/crm/leads")
@RequiredArgsConstructor
public class CrmLeadController {

    private final CrmLeadService service;

    @GetMapping
    public R<Page<CrmLead>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<CrmLead> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<CrmLead> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(CrmLead::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<CrmLead> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody CrmLead entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody CrmLead entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/convert-to-customer")
    public com.cc.framework.base.R<Long> convertToCustomer(@PathVariable Long id) {
        Long customerId = service.convertToCustomer(id);
        return com.cc.framework.base.R.ok(customerId);
    }

}
