package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.sal.SalesDeliveryQueryDTO;
import com.cc.core.entity.fin.FinReceivable;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.inv.InvInventoryTransaction;
import com.cc.core.entity.sal.SalSalesDelivery;
import com.cc.core.entity.sal.SalSalesDeliveryItem;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.entity.sal.SalSalesOrderItem;
import com.cc.core.mapper.fin.FinReceivableMapper;
import com.cc.core.mapper.inv.InvInventoryMapper;
import com.cc.core.mapper.inv.InvInventoryTransactionMapper;
import com.cc.core.mapper.sal.SalSalesDeliveryMapper;
import com.cc.core.service.sal.SalSalesDeliveryItemService;
import com.cc.core.service.sal.SalSalesDeliveryService;
import com.cc.core.service.sal.SalSalesOrderItemService;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 销售出库单服务实现
 */
@Service
@RequiredArgsConstructor
public class SalSalesDeliveryServiceImpl extends ServiceImpl<SalSalesDeliveryMapper, SalSalesDelivery>
        implements SalSalesDeliveryService {

    private final SalSalesDeliveryItemService salesDeliveryItemService;
    private final SalSalesOrderService salesOrderService;
    private final SalSalesOrderItemService salesOrderItemService;
    private final InvInventoryMapper invInventoryMapper;
    private final InvInventoryTransactionMapper invInventoryTransactionMapper;
    private final FinReceivableMapper finReceivableMapper;

    @Override
    public IPage<SalSalesDelivery> page(SalesDeliveryQueryDTO query) {
        Page<SalSalesDelivery> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<SalSalesDelivery> wrapper = new LambdaQueryWrapper<>();
        if (query.getDeliveryNo() != null && !query.getDeliveryNo().isEmpty()) {
            wrapper.like(SalSalesDelivery::getDeliveryNo, query.getDeliveryNo());
        }
        if (query.getCustomerId() != null) {
            wrapper.eq(SalSalesDelivery::getCustomerId, query.getCustomerId());
        }
        if (query.getOrderId() != null) {
            wrapper.eq(SalSalesDelivery::getOrderId, query.getOrderId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(SalSalesDelivery::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(SalSalesDelivery::getDeliveryDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(SalSalesDelivery::getDeliveryDate, query.getEndDate());
        }
        wrapper.orderByDesc(SalSalesDelivery::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<SalSalesDeliveryItem> getItems(Long deliveryId) {
        return salesDeliveryItemService.list(
                new LambdaQueryWrapper<SalSalesDeliveryItem>()
                        .eq(SalSalesDeliveryItem::getDeliveryId, deliveryId)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        SalSalesDelivery delivery = this.getById(id);
        if (delivery == null) {
            throw new RuntimeException("销售出库单不存在");
        }
        if (delivery.getStatus() != 1) {
            throw new RuntimeException("只有待审核状态的出库单才能审核");
        }

        // 更新出库单状态
        delivery.setStatus(2); // 已审核
        delivery.setApproverId(SecurityUtil.getUserId());
        delivery.setApproveTime(LocalDateTime.now());
        this.updateById(delivery);

        // 获取出库单明细
        List<SalSalesDeliveryItem> items = this.getItems(id);
        if (items.isEmpty()) {
            throw new RuntimeException("出库单明细为空");
        }

        Long warehouseId = delivery.getWarehouseId();
        Long operatorId = SecurityUtil.getUserId();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalQuantity = BigDecimal.ZERO;

        // 遍历明细，扣减库存和锁定数量、写库存流水
        for (SalSalesDeliveryItem item : items) {
            BigDecimal quantity = item.getQuantity();
            BigDecimal price = item.getPrice();
            BigDecimal amount = quantity.multiply(price);
            String batchNo = item.getBatchNo() != null ? item.getBatchNo() : "";

            totalAmount = totalAmount.add(amount);
            totalQuantity = totalQuantity.add(quantity);

            // 查询库存记录（仓库 + 商品 + 批次号）
            InvInventory inventory = invInventoryMapper.selectOne(
                    new LambdaQueryWrapper<InvInventory>()
                            .eq(InvInventory::getWarehouseId, warehouseId)
                            .eq(InvInventory::getProductId, item.getProductId())
                            .eq(InvInventory::getBatchNo, batchNo)
            );

            BigDecimal beforeQuantity = BigDecimal.ZERO;
            BigDecimal costPrice = BigDecimal.ZERO;

            if (inventory != null) {
                beforeQuantity = inventory.getQuantity() != null ? inventory.getQuantity() : BigDecimal.ZERO;
                BigDecimal beforeLocked = inventory.getLockedQuantity() != null
                        ? inventory.getLockedQuantity() : BigDecimal.ZERO;
                costPrice = inventory.getCostPrice() != null ? inventory.getCostPrice() : BigDecimal.ZERO;

                // 扣减库存数量
                BigDecimal newQuantity = beforeQuantity.subtract(quantity);
                if (newQuantity.compareTo(BigDecimal.ZERO) < 0) {
                    newQuantity = BigDecimal.ZERO;
                }

                // 扣减锁定数量
                BigDecimal newLocked = beforeLocked.subtract(quantity);
                if (newLocked.compareTo(BigDecimal.ZERO) < 0) {
                    newLocked = BigDecimal.ZERO;
                }

                // 重新计算总成本
                BigDecimal oldTotalCost = inventory.getTotalCost() != null
                        ? inventory.getTotalCost() : BigDecimal.ZERO;
                BigDecimal newTotalCost = oldTotalCost.subtract(costPrice.multiply(quantity));
                if (newTotalCost.compareTo(BigDecimal.ZERO) < 0) {
                    newTotalCost = BigDecimal.ZERO;
                }

                inventory.setQuantity(newQuantity);
                inventory.setLockedQuantity(newLocked);
                inventory.setTotalCost(newTotalCost);
                invInventoryMapper.updateById(inventory);
            }

            // 写库存流水（销售出库，type=3）
            InvInventoryTransaction transaction = new InvInventoryTransaction();
            transaction.setWarehouseId(warehouseId);
            transaction.setProductId(item.getProductId());
            transaction.setBatchNo(batchNo);
            transaction.setTransactionType(3); // 销售出库
            transaction.setQuantity(quantity);
            transaction.setBeforeQuantity(beforeQuantity);
            transaction.setAfterQuantity(beforeQuantity.subtract(quantity));
            transaction.setCostPrice(costPrice);
            transaction.setSourceType("sales_delivery");
            transaction.setSourceId(delivery.getId());
            transaction.setSourceNo(delivery.getDeliveryNo());
            transaction.setTransactionTime(LocalDateTime.now());
            transaction.setOperatorId(operatorId);
            invInventoryTransactionMapper.insert(transaction);

            // 如果关联了销售订单明细，更新订单明细的已发货数量
            if (item.getOrderItemId() != null) {
                SalSalesOrderItem orderItem = salesOrderItemService.getById(item.getOrderItemId());
                if (orderItem != null) {
                    BigDecimal newDelivered = orderItem.getDeliveredQuantity().add(quantity);
                    orderItem.setDeliveredQuantity(newDelivered);
                    salesOrderItemService.updateById(orderItem);
                }
            }
        }

        // 如果关联了销售订单，更新订单的已发货数量和状态
        if (delivery.getOrderId() != null) {
            SalSalesOrder order = salesOrderService.getById(delivery.getOrderId());
            if (order != null) {
                BigDecimal newDelivered = order.getDeliveredQuantity().add(totalQuantity);
                order.setDeliveredQuantity(newDelivered);

                // 判断是否全部发货
                if (newDelivered.compareTo(order.getTotalQuantity()) >= 0) {
                    order.setStatus(4); // 已完成
                } else {
                    order.setStatus(3); // 部分发货
                }
                salesOrderService.updateById(order);
            }
        }

        // 更新出库单合计
        delivery.setTotalQuantity(totalQuantity);
        delivery.setTotalAmount(totalAmount);
        this.updateById(delivery);

        // 生成应收账款记录
        FinReceivable receivable = new FinReceivable();
        receivable.setCustomerId(delivery.getCustomerId());
        receivable.setSourceType("sales_delivery");
        receivable.setSourceId(delivery.getId());
        receivable.setSourceNo(delivery.getDeliveryNo());
        receivable.setAmount(totalAmount);
        receivable.setReceivedAmount(BigDecimal.ZERO);
        receivable.setBalance(totalAmount);
        receivable.setStatus(0); // 未核销
        finReceivableMapper.insert(receivable);
    }

    /**
     * 生成出库单编号：SD + yyyyMMddHHmmss + 4位随机数
     */
    public static String generateDeliveryNo() {
        return "SD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ThreadLocalRandom.current().nextInt(1000, 9999);
    }
}
