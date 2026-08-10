package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.pur.PurchaseReceiptQueryDTO;
import com.cc.core.entity.fin.FinPayable;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.inv.InvInventoryTransaction;
import com.cc.core.entity.pur.PurPurchaseOrder;
import com.cc.core.entity.pur.PurPurchaseOrderItem;
import com.cc.core.entity.pur.PurPurchaseReceipt;
import com.cc.core.entity.pur.PurPurchaseReceiptItem;
import com.cc.core.mapper.fin.FinPayableMapper;
import com.cc.core.mapper.inv.InvInventoryMapper;
import com.cc.core.mapper.inv.InvInventoryTransactionMapper;
import com.cc.core.mapper.pur.PurPurchaseReceiptMapper;
import com.cc.core.service.pur.PurPurchaseOrderItemService;
import com.cc.core.service.pur.PurPurchaseOrderService;
import com.cc.core.service.pur.PurPurchaseReceiptItemService;
import com.cc.core.service.pur.PurPurchaseReceiptService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 采购入库单服务实现
 */
@Service
@RequiredArgsConstructor
public class PurPurchaseReceiptServiceImpl extends ServiceImpl<PurPurchaseReceiptMapper, PurPurchaseReceipt>
        implements PurPurchaseReceiptService {

    private final PurPurchaseReceiptItemService purchaseReceiptItemService;
    private final PurPurchaseOrderService purchaseOrderService;
    private final PurPurchaseOrderItemService purchaseOrderItemService;
    private final InvInventoryMapper invInventoryMapper;
    private final InvInventoryTransactionMapper invInventoryTransactionMapper;
    private final FinPayableMapper finPayableMapper;

    @Override
    public IPage<PurPurchaseReceipt> page(PurchaseReceiptQueryDTO query) {
        Page<PurPurchaseReceipt> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<PurPurchaseReceipt> wrapper = new LambdaQueryWrapper<>();
        if (query.getReceiptNo() != null && !query.getReceiptNo().isEmpty()) {
            wrapper.like(PurPurchaseReceipt::getReceiptNo, query.getReceiptNo());
        }
        if (query.getSupplierId() != null) {
            wrapper.eq(PurPurchaseReceipt::getSupplierId, query.getSupplierId());
        }
        if (query.getOrderId() != null) {
            wrapper.eq(PurPurchaseReceipt::getOrderId, query.getOrderId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(PurPurchaseReceipt::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(PurPurchaseReceipt::getReceiptDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(PurPurchaseReceipt::getReceiptDate, query.getEndDate());
        }
        wrapper.orderByDesc(PurPurchaseReceipt::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<PurPurchaseReceiptItem> getItems(Long receiptId) {
        return purchaseReceiptItemService.list(
                new LambdaQueryWrapper<PurPurchaseReceiptItem>()
                        .eq(PurPurchaseReceiptItem::getReceiptId, receiptId)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        PurPurchaseReceipt receipt = this.getById(id);
        if (receipt == null) {
            throw new RuntimeException("采购入库单不存在");
        }
        if (receipt.getStatus() != 1) {
            throw new RuntimeException("只有待审核状态的入库单才能审核");
        }

        // 更新入库单状态
        receipt.setStatus(2); // 已审核
        receipt.setApproverId(SecurityUtil.getUserId());
        receipt.setApproveTime(LocalDateTime.now());
        this.updateById(receipt);

        // 获取入库单明细
        List<PurPurchaseReceiptItem> items = this.getItems(id);
        if (items.isEmpty()) {
            throw new RuntimeException("入库单明细为空");
        }

        Long warehouseId = receipt.getWarehouseId();
        Long operatorId = SecurityUtil.getUserId();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalQuantity = BigDecimal.ZERO;

        // 遍历明细，更新库存、写流水
        for (PurPurchaseReceiptItem item : items) {
            BigDecimal quantity = item.getQuantity();
            BigDecimal price = item.getPrice();
            BigDecimal amount = quantity.multiply(price);
            String batchNo = item.getBatchNo() != null ? item.getBatchNo() : "";

            totalAmount = totalAmount.add(amount);
            totalQuantity = totalQuantity.add(quantity);

            // 查询现有库存记录（仓库 + 商品 + 批次号）
            InvInventory inventory = invInventoryMapper.selectOne(
                    new LambdaQueryWrapper<InvInventory>()
                            .eq(InvInventory::getWarehouseId, warehouseId)
                            .eq(InvInventory::getProductId, item.getProductId())
                            .eq(InvInventory::getBatchNo, batchNo)
            );

            BigDecimal beforeQuantity;
            BigDecimal newCostPrice;

            if (inventory == null) {
                // 新增库存记录
                inventory = new InvInventory();
                inventory.setWarehouseId(warehouseId);
                inventory.setProductId(item.getProductId());
                inventory.setBatchNo(batchNo);
                inventory.setProductionDate(item.getProductionDate());
                inventory.setExpiryDate(item.getExpiryDate());
                inventory.setQuantity(quantity);
                inventory.setLockedQuantity(BigDecimal.ZERO);
                inventory.setCostPrice(price);
                inventory.setTotalCost(amount);
                beforeQuantity = BigDecimal.ZERO;
                newCostPrice = price;
                invInventoryMapper.insert(inventory);
            } else {
                // 更新库存：增加数量，重新计算成本
                beforeQuantity = inventory.getQuantity();
                BigDecimal oldTotalCost = inventory.getTotalCost();
                BigDecimal newQuantity = beforeQuantity.add(quantity);
                BigDecimal newTotalCost = oldTotalCost.add(amount);
                newCostPrice = newQuantity.compareTo(BigDecimal.ZERO) > 0
                        ? newTotalCost.divide(newQuantity, 4, RoundingMode.HALF_UP)
                        : price;

                inventory.setQuantity(newQuantity);
                inventory.setTotalCost(newTotalCost);
                inventory.setCostPrice(newCostPrice);
                // 如果原库存没有批次信息，补充批次信息
                if (inventory.getProductionDate() == null) {
                    inventory.setProductionDate(item.getProductionDate());
                }
                if (inventory.getExpiryDate() == null) {
                    inventory.setExpiryDate(item.getExpiryDate());
                }
                invInventoryMapper.updateById(inventory);
            }

            // 写库存流水
            InvInventoryTransaction transaction = new InvInventoryTransaction();
            transaction.setWarehouseId(warehouseId);
            transaction.setProductId(item.getProductId());
            transaction.setBatchNo(batchNo);
            transaction.setTransactionType(1); // 采购入库
            transaction.setQuantity(quantity);
            transaction.setBeforeQuantity(beforeQuantity);
            transaction.setAfterQuantity(beforeQuantity.add(quantity));
            transaction.setCostPrice(newCostPrice);
            transaction.setSourceType("purchase_receipt");
            transaction.setSourceId(receipt.getId());
            transaction.setSourceNo(receipt.getReceiptNo());
            transaction.setTransactionTime(LocalDateTime.now());
            transaction.setOperatorId(operatorId);
            invInventoryTransactionMapper.insert(transaction);

            // 如果关联了采购订单明细，更新订单明细的已收数量
            if (item.getOrderItemId() != null) {
                PurPurchaseOrderItem orderItem = purchaseOrderItemService.getById(item.getOrderItemId());
                if (orderItem != null) {
                    BigDecimal newReceived = orderItem.getReceivedQuantity().add(quantity);
                    orderItem.setReceivedQuantity(newReceived);
                    purchaseOrderItemService.updateById(orderItem);
                }
            }
        }

        // 如果关联了采购订单，更新订单的已收数量和状态
        if (receipt.getOrderId() != null) {
            PurPurchaseOrder order = purchaseOrderService.getById(receipt.getOrderId());
            if (order != null) {
                BigDecimal newReceived = order.getReceivedQuantity().add(totalQuantity);
                order.setReceivedQuantity(newReceived);

                // 判断是否全部到货
                if (newReceived.compareTo(order.getTotalQuantity()) >= 0) {
                    order.setStatus(4); // 已完成
                } else {
                    order.setStatus(3); // 部分到货
                }
                purchaseOrderService.updateById(order);
            }
        }

        // 更新入库单合计
        receipt.setTotalQuantity(totalQuantity);
        receipt.setTotalAmount(totalAmount);
        this.updateById(receipt);

        // 生成应付账款记录
        FinPayable payable = new FinPayable();
        payable.setSupplierId(receipt.getSupplierId());
        payable.setSourceType("purchase_receipt");
        payable.setSourceId(receipt.getId());
        payable.setSourceNo(receipt.getReceiptNo());
        payable.setAmount(totalAmount);
        payable.setPaidAmount(BigDecimal.ZERO);
        payable.setBalance(totalAmount);
        payable.setStatus(0); // 未核销
        finPayableMapper.insert(payable);
    }
}
