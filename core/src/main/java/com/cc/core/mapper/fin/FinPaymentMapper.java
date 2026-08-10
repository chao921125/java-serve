package com.cc.core.mapper.fin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.fin.FinPayment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 付款单 Mapper
 */
@Mapper
public interface FinPaymentMapper extends BaseMapper<FinPayment> {
}
