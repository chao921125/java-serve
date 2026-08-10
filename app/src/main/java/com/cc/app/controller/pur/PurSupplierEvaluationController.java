package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.pur.PurSupplierEvaluation;
import com.cc.core.entity.pur.PurSupplierEvaluationCriteria;
import com.cc.core.service.pur.PurSupplierEvaluationCriteriaService;
import com.cc.core.service.pur.PurSupplierEvaluationService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * PurSupplierEvaluation 控制器
 */
@RestController
@RequestMapping("/api/v1/supplier-evaluations")
@RequiredArgsConstructor
public class PurSupplierEvaluationController {

    private final PurSupplierEvaluationService service;
    private final PurSupplierEvaluationCriteriaService criteriaService;

    @GetMapping
    public R<Page<PurSupplierEvaluation>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PurSupplierEvaluation> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<PurSupplierEvaluation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(PurSupplierEvaluation::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<PurSupplierEvaluation> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody PurSupplierEvaluation entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody PurSupplierEvaluation entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ==== Business Endpoints ====

    @PostMapping("/{id}/calculate-score")
    public com.cc.framework.base.R<Void> calculateTotalScore(@PathVariable Long id) {
        service.calculateTotalScore(id);
        return com.cc.framework.base.R.ok();
    }

    // ==== 评估维度配置 ====

    @GetMapping("/criteria")
    public R<java.util.List<PurSupplierEvaluationCriteria>> criteriaList() {
        LambdaQueryWrapper<PurSupplierEvaluationCriteria> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(PurSupplierEvaluationCriteria::getSortOrder);
        return R.ok(criteriaService.list(wrapper));
    }

    @PutMapping("/criteria")
    public R<Void> updateCriteria(@RequestBody java.util.List<PurSupplierEvaluationCriteria> criteriaList) {
        criteriaService.updateBatchById(criteriaList);
        return R.ok();
    }

}
