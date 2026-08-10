package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.fin.FinOtherIncome;
import com.cc.core.service.fin.FinOtherIncomeService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 其他收支控制器
 */
@RestController
@RequestMapping("/api/v1/other-incomes")
@RequiredArgsConstructor
public class FinOtherIncomeController {

    private final FinOtherIncomeService otherIncomeService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<Page<FinOtherIncome>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status) {
        Page<FinOtherIncome> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<FinOtherIncome> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(FinOtherIncome::getType, type);
        }
        if (status != null) {
            wrapper.eq(FinOtherIncome::getStatus, status);
        }
        wrapper.orderByDesc(FinOtherIncome::getCreateTime);
        return R.ok(otherIncomeService.page(p, wrapper));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<FinOtherIncome> detail(@PathVariable Long id) {
        FinOtherIncome otherIncome = otherIncomeService.getById(id);
        if (otherIncome == null) {
            return R.fail("其他收支不存在");
        }
        return R.ok(otherIncome);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody FinOtherIncome otherIncome) {
        otherIncome.setStatus(0);
        otherIncomeService.save(otherIncome);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody FinOtherIncome otherIncome) {
        otherIncome.setId(id);
        otherIncomeService.updateById(otherIncome);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return otherIncomeService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
