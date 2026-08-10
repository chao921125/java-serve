package com.cc.core.service.inv;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.inv.StockTakeQueryDTO;
import com.cc.core.entity.inv.InvStockTake;
import com.cc.core.entity.inv.InvStockTakeItem;

import java.util.List;

/**
 * 盘点单服务接口
 */
public interface InvStockTakeService extends IService<InvStockTake> {

    /**
     * 分页查询盘点单
     */
    IPage<InvStockTake> page(StockTakeQueryDTO query);

    /**
     * 审核盘点单
     */
    void approve(Long id);

    /**
     * 获取盘点单明细
     */
    List<InvStockTakeItem> getItems(Long takeId);
}
