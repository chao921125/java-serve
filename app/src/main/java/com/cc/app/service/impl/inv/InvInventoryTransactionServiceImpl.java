package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.inv.InvInventoryTransaction;
import com.cc.core.mapper.inv.InvInventoryTransactionMapper;
import com.cc.core.service.inv.InvInventoryTransactionService;
import org.springframework.stereotype.Service;

/**
 * 库存流水服务实现
 */
@Service
public class InvInventoryTransactionServiceImpl
        extends ServiceImpl<InvInventoryTransactionMapper, InvInventoryTransaction>
        implements InvInventoryTransactionService {

    @Override
    public IPage<InvInventoryTransaction> page(Long productId, Long warehouseId,
                                                Integer page, Integer pageSize) {
        Page<InvInventoryTransaction> p = new Page<>(
                page != null ? page : 1,
                pageSize != null ? pageSize : 10
        );
        LambdaQueryWrapper<InvInventoryTransaction> wrapper = new LambdaQueryWrapper<>();
        if (productId != null) {
            wrapper.eq(InvInventoryTransaction::getProductId, productId);
        }
        if (warehouseId != null) {
            wrapper.eq(InvInventoryTransaction::getWarehouseId, warehouseId);
        }
        wrapper.orderByDesc(InvInventoryTransaction::getTransactionTime);
        return this.page(p, wrapper);
    }
}
