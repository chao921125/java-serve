package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.fin.FinAccountTransaction;
import com.cc.core.mapper.fin.FinAccountTransactionMapper;
import com.cc.core.service.fin.FinAccountTransactionService;
import org.springframework.stereotype.Service;

/**
 * 资金账户流水服务实现
 */
@Service
public class FinAccountTransactionServiceImpl
        extends ServiceImpl<FinAccountTransactionMapper, FinAccountTransaction>
        implements FinAccountTransactionService {

    @Override
    public IPage<FinAccountTransaction> page(Long accountId, Integer page, Integer pageSize) {
        Page<FinAccountTransaction> p = new Page<>(
                page != null ? page : 1,
                pageSize != null ? pageSize : 10
        );
        LambdaQueryWrapper<FinAccountTransaction> wrapper = new LambdaQueryWrapper<>();
        if (accountId != null) {
            wrapper.eq(FinAccountTransaction::getAccountId, accountId);
        }
        wrapper.orderByDesc(FinAccountTransaction::getTransactionTime);
        return this.page(p, wrapper);
    }
}
