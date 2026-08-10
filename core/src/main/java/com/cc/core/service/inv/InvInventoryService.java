package com.cc.core.service.inv;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.inv.InventoryQueryDTO;
import com.cc.core.entity.inv.InvInventory;

import java.util.List;

/**
 * 库存服务接口
 */
public interface InvInventoryService extends IService<InvInventory> {

    /**
     * 分页查询库存
     */
    IPage<InvInventory> page(InventoryQueryDTO query);

    /**
     * 根据商品 ID 查询库存
     */
    List<InvInventory> getByProduct(Long productId);

    /**
     * 增加库存
     */
    void increaseStock(Long productId, Long warehouseId, java.math.BigDecimal quantity, String batchNo);

    /**
     * 减少库存
     */
    void decreaseStock(Long productId, Long warehouseId, java.math.BigDecimal quantity, String batchNo);
}
