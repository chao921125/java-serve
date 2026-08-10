package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.inv.InvStockReservation;
import com.cc.core.mapper.inv.InvStockReservationMapper;
import com.cc.core.service.inv.InvStockReservationService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.service.inv.InvInventoryService;

/**
 * InvStockReservation 服务实现
 */
@Service
@RequiredArgsConstructor
public class InvStockReservationServiceImpl extends ServiceImpl<InvStockReservationMapper, InvStockReservation> implements InvStockReservationService {
    private final InvInventoryService inventoryService;


    // ==== Business Logic Methods ====

    @Override
    public void release(Long id) {
        InvStockReservation reservation = getById(id);
        if (reservation.getStatus() != 0) throw new RuntimeException("仅已预留状态可释放");
        // 释放锁定库存
        InvInventory inv = getInventoryByProductAndWarehouse(reservation.getProductId(), reservation.getWarehouseId(), reservation.getBatchNo());
        if (inv != null) {
            inv.setLockedQuantity(inv.getLockedQuantity().subtract(reservation.getQuantity()));
            inventoryService.updateById(inv);
        }
        reservation.setStatus(1);
        reservation.setReleasedQuantity(reservation.getQuantity());
        updateById(reservation);
    }

    @Override
    public void cancel(Long id) {
        InvStockReservation reservation = getById(id);
        if (reservation.getStatus() != 0) throw new RuntimeException("仅已预留状态可取消");
        InvInventory inv = getInventoryByProductAndWarehouse(reservation.getProductId(), reservation.getWarehouseId(), reservation.getBatchNo());
        if (inv != null) {
            inv.setLockedQuantity(inv.getLockedQuantity().subtract(reservation.getQuantity()));
            inventoryService.updateById(inv);
        }
        reservation.setStatus(3);
        updateById(reservation);
    }

    private InvInventory getInventoryByProductAndWarehouse(Long productId, Long warehouseId, String batchNo) {
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<InvInventory> wrapper =
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<InvInventory>()
                .eq(InvInventory::getProductId, productId)
                .eq(InvInventory::getWarehouseId, warehouseId);
        if (batchNo != null && !batchNo.isEmpty()) {
            wrapper.eq(InvInventory::getBatchNo, batchNo);
        }
        return inventoryService.getOne(wrapper);
    }

}
