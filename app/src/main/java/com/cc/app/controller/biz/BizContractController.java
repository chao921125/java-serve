package com.cc.app.controller.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.biz.BizContract;
import com.cc.core.service.biz.BizContractService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * BizContract 控制器
 */
@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class BizContractController {

    private final BizContractService service;

    @GetMapping
    public R<Page<BizContract>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<BizContract> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<BizContract> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(BizContract::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<BizContract> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody BizContract entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody BizContract entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/record-change")
    public com.cc.framework.base.R<Void> recordChange(@PathVariable Long id,
            @RequestParam String changeType, @RequestParam String beforeValue,
            @RequestParam String afterValue, @RequestParam String reason,
            @RequestParam String changedBy) {
        service.recordChange(id, changeType, beforeValue, afterValue, reason, changedBy);
        return com.cc.framework.base.R.ok();
    }

    @PostMapping("/{id}/execute-query")
    public com.cc.framework.base.R<java.util.List<java.util.Map<String, Object>>> executeQuery(@PathVariable Long id) {
        return com.cc.framework.base.R.ok(service.executeQuery(id));
    }

    @GetMapping("/by-code/{code}")
    public com.cc.framework.base.R<BizContract> getByCode(@PathVariable String code) {
        return com.cc.framework.base.R.ok(service.getByCode(code));
    }

}
