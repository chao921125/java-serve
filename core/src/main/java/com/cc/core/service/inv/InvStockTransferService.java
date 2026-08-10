package com.cc.core.service.inv;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.inv.StockTransferQueryDTO;
import com.cc.core.entity.inv.InvStockTransfer;
import com.cc.core.entity.inv.InvStockTransferItem;

import java.util.List;

/**
 * 调拨单服务接口
 */
public interface InvStockTransferService extends IService<InvStockTransfer> {

    /**
     * 分页查询调拨单
     */
    IPage<InvStockTransfer> page(StockTransferQueryDTO query);

    /**
     * 审核调拨单
     */
    void approve(Long id);

    /**
     * 获取调拨单明细
     */
    List<InvStockTransferItem> getItems(Long transferId);
}
