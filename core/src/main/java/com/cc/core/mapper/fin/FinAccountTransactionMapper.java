package com.cc.core.mapper.fin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.fin.FinAccountTransaction;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资金账户流水 Mapper
 */
@Mapper
public interface FinAccountTransactionMapper extends BaseMapper<FinAccountTransaction> {
}
