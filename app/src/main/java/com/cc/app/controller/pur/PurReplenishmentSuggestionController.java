package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.pur.PurReplenishmentSuggestion;
import com.cc.core.service.pur.PurReplenishmentSuggestionService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * PurReplenishmentSuggestion 控制器
 */
@RestController
@RequestMapping("/api/v1/replenishment-suggestions")
@RequiredArgsConstructor
public class PurReplenishmentSuggestionController {

    private final PurReplenishmentSuggestionService service;

    @GetMapping
    public R<Page<PurReplenishmentSuggestion>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PurReplenishmentSuggestion> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<PurReplenishmentSuggestion> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(PurReplenishmentSuggestion::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<PurReplenishmentSuggestion> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody PurReplenishmentSuggestion entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody PurReplenishmentSuggestion entity) {
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
    public com.cc.framework.base.R<Integer> scanAndGenerate() {
        int count = service.scanAndGenerate();
        return com.cc.framework.base.R.ok(count);
    }

    @PostMapping("/generate")
    public com.cc.framework.base.R<Integer> generate() {
        int count = service.scanAndGenerate();
        return com.cc.framework.base.R.ok(count);
    }

    @PostMapping("/{id}/convert-to-requisition")
    public com.cc.framework.base.R<Long> convertToRequisition(@PathVariable Long id) {
        Long reqId = service.convertToRequisition(id);
        return com.cc.framework.base.R.ok(reqId);
    }

    @PostMapping("/{id}/ignore")
    public com.cc.framework.base.R<Void> ignore(@PathVariable Long id) {
        service.ignore(id);
        return com.cc.framework.base.R.ok();
    }

}
