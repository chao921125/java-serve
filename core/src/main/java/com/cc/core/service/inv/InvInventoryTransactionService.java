package com.cc.core.service.inv;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.inv.InvInventoryTransaction;

/**
 * 库存流水服务接口
 */
public interface InvInventoryTransactionService extends IService<InvInventoryTransaction> {

    /**
     * 分页查询库存流水
     */
    IPage<InvInventoryTransaction> page(Long productId, Long warehouseId, Integer page, Integer pageSize);
}
