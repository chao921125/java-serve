package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.fin.ExpenseQueryDTO;
import com.cc.core.entity.fin.FinExpense;
import com.cc.core.mapper.fin.FinExpenseMapper;
import com.cc.core.service.fin.FinExpenseService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 费用支出服务实现
 */
@Service
@RequiredArgsConstructor
public class FinExpenseServiceImpl extends ServiceImpl<FinExpenseMapper, FinExpense>
        implements FinExpenseService {

    private final FinAccountServiceImpl accountService;

    @Override
    public IPage<FinExpense> page(ExpenseQueryDTO query) {
        Page<FinExpense> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<FinExpense> wrapper = new LambdaQueryWrapper<>();
        if (query.getExpenseNo() != null && !query.getExpenseNo().isEmpty()) {
            wrapper.like(FinExpense::getExpenseNo, query.getExpenseNo());
        }
        if (query.getDepartmentId() != null) {
            wrapper.eq(FinExpense::getDepartmentId, query.getDepartmentId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(FinExpense::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(FinExpense::getExpenseDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(FinExpense::getExpenseDate, query.getEndDate());
        }
        wrapper.orderByDesc(FinExpense::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        FinExpense expense = this.getById(id);
        if (expense == null) {
            throw new RuntimeException("费用支出不存在");
        }
        if (expense.getStatus() != 1) {
            throw new RuntimeException("只有待审状态的费用支出才能审核");
        }

        // 扣减账户余额
        accountService.accountOut(
                expense.getAccountId(), expense.getAmount(),
                "EXPENSE", expense.getId(), expense.getExpenseNo(),
                "费用支出审核：" + expense.getExpenseNo()
        );

        expense.setStatus(2); // 已审
        expense.setApproverId(SecurityUtil.getUserId());
        expense.setApproveTime(LocalDateTime.now());
        this.updateById(expense);
    }
}
