package com.cc.app.controller.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.inv.InvExpiryAlert;
import com.cc.core.service.inv.InvExpiryAlertService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * InvExpiryAlert 控制器
 */
@RestController
@RequestMapping("/api/v1/expiry-alerts")
@RequiredArgsConstructor
public class InvExpiryAlertController {

    private final InvExpiryAlertService service;

    @GetMapping
    public R<Page<InvExpiryAlert>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<InvExpiryAlert> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<InvExpiryAlert> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(InvExpiryAlert::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<InvExpiryAlert> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody InvExpiryAlert entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody InvExpiryAlert entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/scan")
    public com.cc.framework.base.R<Integer> scanAndAlert() {
        int count = service.scanAndAlert();
        return com.cc.framework.base.R.ok(count);
    }

    @PutMapping("/{id}/handle")
    public com.cc.framework.base.R<Void> handle(@PathVariable Long id, @RequestParam String handleMethod) {
        service.handle(id, handleMethod);
        return com.cc.framework.base.R.ok();
    }

    @GetMapping("/stats")
    public com.cc.framework.base.R<java.util.Map<String, Object>> stats() {
        return com.cc.framework.base.R.ok(service.getStats());
    }

}
