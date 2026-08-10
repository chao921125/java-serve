package com.cc.core.service.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.fin.FinAccountTransaction;

/**
 * 资金账户流水服务接口
 */
public interface FinAccountTransactionService extends IService<FinAccountTransaction> {

    /**
     * 分页查询账户流水
     */
    IPage<FinAccountTransaction> page(Long accountId, Integer page, Integer pageSize);
}
