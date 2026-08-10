package com.cc.core.mapper.fin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.fin.FinPaymentItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 付款单明细 Mapper
 */
@Mapper
public interface FinPaymentItemMapper extends BaseMapper<FinPaymentItem> {
}
