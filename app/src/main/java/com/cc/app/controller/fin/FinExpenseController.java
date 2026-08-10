package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.fin.ExpenseQueryDTO;
import com.cc.core.entity.fin.FinExpense;
import com.cc.core.service.fin.FinExpenseService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 费用支出控制器
 */
@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class FinExpenseController {

    private final FinExpenseService expenseService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<FinExpense>> list(ExpenseQueryDTO query) {
        return R.ok(expenseService.page(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<FinExpense> detail(@PathVariable Long id) {
        FinExpense expense = expenseService.getById(id);
        if (expense == null) {
            return R.fail("费用支出不存在");
        }
        return R.ok(expense);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody FinExpense expense) {
        expense.setStatus(0);
        expenseService.save(expense);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody FinExpense expense) {
        expense.setId(id);
        expenseService.updateById(expense);
        return R.ok();
    }

    /**
     * 删除（仅草稿状态）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        FinExpense expense = expenseService.getById(id);
        if (expense == null) {
            return R.fail("费用支出不存在");
        }
        if (expense.getStatus() != 0) {
            return R.fail("只有草稿状态的费用支出才能删除");
        }
        return expenseService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        expenseService.approve(id);
        return R.ok();
    }
}
