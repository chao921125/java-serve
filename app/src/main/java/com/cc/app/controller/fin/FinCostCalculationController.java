package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.fin.FinCostCalculation;
import com.cc.core.service.fin.FinCostCalculationService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * FinCostCalculation 控制器
 */
@RestController
@RequestMapping("/api/v1/cost-calculations")
@RequiredArgsConstructor
public class FinCostCalculationController {

    private final FinCostCalculationService service;

    @GetMapping
    public R<Page<FinCostCalculation>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<FinCostCalculation> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<FinCostCalculation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(FinCostCalculation::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<FinCostCalculation> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody FinCostCalculation entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody FinCostCalculation entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/recalculate/{productId}")
    public com.cc.framework.base.R<Void> recalculate(@PathVariable Long productId) {
        service.recalculate(productId);
        return com.cc.framework.base.R.ok();
    }

}
