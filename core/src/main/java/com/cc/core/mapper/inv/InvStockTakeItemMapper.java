package com.cc.core.mapper.inv;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.inv.InvStockTakeItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 盘点单明细 Mapper
 */
@Mapper
public interface InvStockTakeItemMapper extends BaseMapper<InvStockTakeItem> {
}
