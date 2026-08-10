package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.pur.PurPurchaseRequisition;
import com.cc.core.service.pur.PurPurchaseRequisitionService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * PurPurchaseRequisition 控制器
 */
@RestController
@RequestMapping("/api/v1/purchase-requisitions")
@RequiredArgsConstructor
public class PurPurchaseRequisitionController {

    private final PurPurchaseRequisitionService service;

    @GetMapping
    public R<Page<PurPurchaseRequisition>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PurPurchaseRequisition> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<PurPurchaseRequisition> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(PurPurchaseRequisition::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<PurPurchaseRequisition> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody PurPurchaseRequisition entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody PurPurchaseRequisition entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/submit")
    public com.cc.framework.base.R<Void> submitForApproval(@PathVariable Long id) {
        service.submitForApproval(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/approve")
    public com.cc.framework.base.R<Void> approve(@PathVariable Long id, @RequestParam Long approverId) {
        service.approve(id, approverId);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/reject")
    public com.cc.framework.base.R<Void> reject(@PathVariable Long id, @RequestParam Long approverId, @RequestParam String reason) {
        service.reject(id, approverId, reason);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/close")
    public com.cc.framework.base.R<Void> close(@PathVariable Long id) {
        service.close(id);
        return com.cc.framework.base.R.ok();
    }

}
