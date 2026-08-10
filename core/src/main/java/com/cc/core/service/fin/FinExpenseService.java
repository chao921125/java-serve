package com.cc.core.service.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.fin.ExpenseQueryDTO;
import com.cc.core.entity.fin.FinExpense;

/**
 * 费用支出服务接口
 */
public interface FinExpenseService extends IService<FinExpense> {

    /**
     * 分页查询费用支出
     */
    IPage<FinExpense> page(ExpenseQueryDTO query);

    /**
     * 审核费用支出 — 扣减账户余额
     */
    void approve(Long id);
}
