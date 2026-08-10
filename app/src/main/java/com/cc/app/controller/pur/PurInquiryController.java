package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.pur.PurInquiry;
import com.cc.core.service.pur.PurInquiryService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * PurInquiry 控制器
 */
@RestController
@RequestMapping("/api/v1/inquiries")
@RequiredArgsConstructor
public class PurInquiryController {

    private final PurInquiryService service;

    @GetMapping
    public R<Page<PurInquiry>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PurInquiry> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<PurInquiry> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(PurInquiry::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<PurInquiry> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody PurInquiry entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody PurInquiry entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/issue")
    public com.cc.framework.base.R<Void> issue(@PathVariable Long id) {
        service.issue(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/compare")
    public com.cc.framework.base.R<Void> compareAndSelect(@PathVariable Long id, @RequestParam Long selectedSupplierId) {
        service.compareAndSelect(id, selectedSupplierId);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/convert-to-order")
    public com.cc.framework.base.R<Long> convertToPurchaseOrder(@PathVariable Long id) {
        Long orderId = service.convertToPurchaseOrder(id);
        return com.cc.framework.base.R.ok(orderId);
    }

    @PostMapping("/{id}/close")
    public com.cc.framework.base.R<Void> close(@PathVariable Long id) {
        service.close(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/quotes")
    public com.cc.framework.base.R<Void> enterQuote(@PathVariable Long id,
            @RequestParam Long supplierId, @RequestParam Long inquiryItemId,
            @RequestParam java.math.BigDecimal unitPrice) {
        service.enterQuote(id, supplierId, inquiryItemId, unitPrice);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/decide")
    public com.cc.framework.base.R<Void> decide(@PathVariable Long id,
            @RequestParam Long selectedSupplierId) {
        service.decide(id, selectedSupplierId);
        return com.cc.framework.base.R.ok();
    }

}
