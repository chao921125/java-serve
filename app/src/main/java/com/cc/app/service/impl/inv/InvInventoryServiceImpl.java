package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.inv.InventoryQueryDTO;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.inv.InvInventoryTransaction;
import com.cc.core.mapper.inv.InvInventoryMapper;
import com.cc.core.service.inv.InvInventoryService;
import com.cc.core.service.inv.InvInventoryTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 库存服务实现
 */
@Service
@RequiredArgsConstructor
public class InvInventoryServiceImpl extends ServiceImpl<InvInventoryMapper, InvInventory>
        implements InvInventoryService {

    private final InvInventoryTransactionService inventoryTransactionService;

    @Override
    public IPage<InvInventory> page(InventoryQueryDTO query) {
        Page<InvInventory> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<InvInventory> wrapper = new LambdaQueryWrapper<>();
        if (query.getWarehouseId() != null) {
            wrapper.eq(InvInventory::getWarehouseId, query.getWarehouseId());
        }
        if (query.getProductId() != null) {
            wrapper.eq(InvInventory::getProductId, query.getProductId());
        }
        if (query.getMinQuantity() != null) {
            wrapper.ge(InvInventory::getQuantity, query.getMinQuantity());
        }
        if (query.getMaxQuantity() != null) {
            wrapper.le(InvInventory::getQuantity, query.getMaxQuantity());
        }
        wrapper.orderByDesc(InvInventory::getUpdateTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<InvInventory> getByProduct(Long productId) {
        return this.list(
                new LambdaQueryWrapper<InvInventory>()
                        .eq(InvInventory::getProductId, productId)
        );
    }

    /**
     * 增加库存并写流水
     *
     * @param warehouseId   仓库 ID
     * @param productId     商品 ID
     * @param batchNo       批次号
     * @param quantity      增加数量
     * @param costPrice     成本单价
     * @param transactionType 流水类型
     * @param sourceType    来源类型
     * @param sourceId      来源 ID
     * @param sourceNo      来源单号
     * @param remark        备注
     */
    @Transactional(rollbackFor = Exception.class)
    public void stockIn(Long warehouseId, Long productId, String batchNo, BigDecimal quantity,
                        BigDecimal costPrice, Integer transactionType, String sourceType,
                        Long sourceId, String sourceNo, String remark) {
        InvInventory inventory = findOrCreate(warehouseId, productId, batchNo);
        BigDecimal beforeQuantity = inventory.getQuantity() != null ? inventory.getQuantity() : BigDecimal.ZERO;
        BigDecimal afterQuantity = beforeQuantity.add(quantity);

        inventory.setQuantity(afterQuantity);
        if (costPrice != null && costPrice.compareTo(BigDecimal.ZERO) > 0) {
            inventory.setCostPrice(costPrice);
        }
        inventory.setTotalCost(afterQuantity.multiply(
                inventory.getCostPrice() != null ? inventory.getCostPrice() : BigDecimal.ZERO));
        inventory.setUpdateTime(LocalDateTime.now());
        this.updateById(inventory);

        recordTransaction(warehouseId, productId, batchNo, transactionType, quantity,
                beforeQuantity, afterQuantity, costPrice, sourceType, sourceId, sourceNo, remark);
    }

    /**
     * 减少库存并写流水
     *
     * @param warehouseId   仓库 ID
     * @param productId     商品 ID
     * @param batchNo       批次号
     * @param quantity      减少数量（正数）
     * @param transactionType 流水类型
     * @param sourceType    来源类型
     * @param sourceId      来源 ID
     * @param sourceNo      来源单号
     * @param remark        备注
     */
    @Transactional(rollbackFor = Exception.class)
    public void stockOut(Long warehouseId, Long productId, String batchNo, BigDecimal quantity,
                         Integer transactionType, String sourceType, Long sourceId,
                         String sourceNo, String remark) {
        InvInventory inventory = findOrCreate(warehouseId, productId, batchNo);
        BigDecimal beforeQuantity = inventory.getQuantity() != null ? inventory.getQuantity() : BigDecimal.ZERO;
        BigDecimal afterQuantity = beforeQuantity.subtract(quantity);

        inventory.setQuantity(afterQuantity);
        inventory.setTotalCost(afterQuantity.multiply(
                inventory.getCostPrice() != null ? inventory.getCostPrice() : BigDecimal.ZERO));
        inventory.setUpdateTime(LocalDateTime.now());
        this.updateById(inventory);

        BigDecimal costPrice = inventory.getCostPrice();
        recordTransaction(warehouseId, productId, batchNo, transactionType, quantity,
                beforeQuantity, afterQuantity, costPrice, sourceType, sourceId, sourceNo, remark);
    }

    /**
     * 查找或创建库存记录
     */
    private InvInventory findOrCreate(Long warehouseId, Long productId, String batchNo) {
        LambdaQueryWrapper<InvInventory> wrapper = new LambdaQueryWrapper<InvInventory>()
                .eq(InvInventory::getWarehouseId, warehouseId)
                .eq(InvInventory::getProductId, productId);
        if (batchNo != null && !batchNo.isEmpty()) {
            wrapper.eq(InvInventory::getBatchNo, batchNo);
        } else {
            wrapper.isNull(InvInventory::getBatchNo);
        }
        InvInventory inventory = this.getOne(wrapper);
        if (inventory == null) {
            inventory = new InvInventory();
            inventory.setWarehouseId(warehouseId);
            inventory.setProductId(productId);
            inventory.setBatchNo(batchNo);
            inventory.setQuantity(BigDecimal.ZERO);
            inventory.setLockedQuantity(BigDecimal.ZERO);
            inventory.setCostPrice(BigDecimal.ZERO);
            inventory.setTotalCost(BigDecimal.ZERO);
            inventory.setUpdateTime(LocalDateTime.now());
            this.save(inventory);
        }
        return inventory;
    }

    /**
     * 记录库存流水
     */
    private void recordTransaction(Long warehouseId, Long productId, String batchNo,
                                   Integer transactionType, BigDecimal quantity,
                                   BigDecimal beforeQuantity, BigDecimal afterQuantity,
                                   BigDecimal costPrice, String sourceType, Long sourceId,
                                   String sourceNo, String remark) {
        InvInventoryTransaction transaction = new InvInventoryTransaction();
        transaction.setWarehouseId(warehouseId);
        transaction.setProductId(productId);
        transaction.setBatchNo(batchNo);
        transaction.setTransactionType(transactionType);
        transaction.setQuantity(quantity);
        transaction.setBeforeQuantity(beforeQuantity);
        transaction.setAfterQuantity(afterQuantity);
        transaction.setCostPrice(costPrice);
        transaction.setSourceType(sourceType);
        transaction.setSourceId(sourceId);
        transaction.setSourceNo(sourceNo);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setRemark(remark);
        inventoryTransactionService.save(transaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseStock(Long productId, Long warehouseId, java.math.BigDecimal quantity, String batchNo) {
        stockIn(warehouseId, productId, batchNo, quantity, null, 1, "EXCHANGE_RETURN", null, null, "换货退回入库");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseStock(Long productId, Long warehouseId, java.math.BigDecimal quantity, String batchNo) {
        stockOut(warehouseId, productId, batchNo, quantity, 2, "EXCHANGE_OUT", null, null, "换货发出出库");
    }
}
