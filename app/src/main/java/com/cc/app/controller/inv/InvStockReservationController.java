package com.cc.app.controller.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.inv.InvStockReservation;
import com.cc.core.service.inv.InvStockReservationService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * InvStockReservation 控制器
 */
@RestController
@RequestMapping("/api/v1/stock-reservations")
@RequiredArgsConstructor
public class InvStockReservationController {

    private final InvStockReservationService service;

    @GetMapping
    public R<Page<InvStockReservation>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<InvStockReservation> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<InvStockReservation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(InvStockReservation::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<InvStockReservation> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody InvStockReservation entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody InvStockReservation entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/release")
    public com.cc.framework.base.R<Void> release(@PathVariable Long id) {
        service.release(id);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/cancel")
    public com.cc.framework.base.R<Void> cancel(@PathVariable Long id) {
        service.cancel(id);
        return com.cc.framework.base.R.ok();
    }

}
