package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.pur.PurchaseReturnQueryDTO;
import com.cc.core.entity.fin.FinPayable;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.inv.InvInventoryTransaction;
import com.cc.core.entity.pur.PurPurchaseReturn;
import com.cc.core.entity.pur.PurPurchaseReturnItem;
import com.cc.core.mapper.fin.FinPayableMapper;
import com.cc.core.mapper.inv.InvInventoryMapper;
import com.cc.core.mapper.inv.InvInventoryTransactionMapper;
import com.cc.core.mapper.pur.PurPurchaseReturnMapper;
import com.cc.core.service.pur.PurPurchaseReturnItemService;
import com.cc.core.service.pur.PurPurchaseReturnService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购退货单服务实现
 */
@Service
@RequiredArgsConstructor
public class PurPurchaseReturnServiceImpl extends ServiceImpl<PurPurchaseReturnMapper, PurPurchaseReturn>
        implements PurPurchaseReturnService {

    private final PurPurchaseReturnItemService purchaseReturnItemService;
    private final InvInventoryMapper invInventoryMapper;
    private final InvInventoryTransactionMapper invInventoryTransactionMapper;
    private final FinPayableMapper finPayableMapper;

    @Override
    public IPage<PurPurchaseReturn> page(PurchaseReturnQueryDTO query) {
        Page<PurPurchaseReturn> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<PurPurchaseReturn> wrapper = new LambdaQueryWrapper<>();
        if (query.getReturnNo() != null && !query.getReturnNo().isEmpty()) {
            wrapper.like(PurPurchaseReturn::getReturnNo, query.getReturnNo());
        }
        if (query.getSupplierId() != null) {
            wrapper.eq(PurPurchaseReturn::getSupplierId, query.getSupplierId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(PurPurchaseReturn::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(PurPurchaseReturn::getReturnDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(PurPurchaseReturn::getReturnDate, query.getEndDate());
        }
        wrapper.orderByDesc(PurPurchaseReturn::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<PurPurchaseReturnItem> getItems(Long returnId) {
        return purchaseReturnItemService.list(
                new LambdaQueryWrapper<PurPurchaseReturnItem>()
                        .eq(PurPurchaseReturnItem::getReturnId, returnId)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        PurPurchaseReturn returnOrder = this.getById(id);
        if (returnOrder == null) {
            throw new RuntimeException("采购退货单不存在");
        }
        if (returnOrder.getStatus() != 1) {
            throw new RuntimeException("只有待审核状态的退货单才能审核");
        }

        // 更新退货单状态
        returnOrder.setStatus(2); // 已审核
        returnOrder.setApproverId(SecurityUtil.getUserId());
        returnOrder.setApproveTime(LocalDateTime.now());
        this.updateById(returnOrder);

        // 获取退货明细
        List<PurPurchaseReturnItem> items = this.getItems(id);
        if (items.isEmpty()) {
            throw new RuntimeException("退货单明细为空");
        }

        Long warehouseId = returnOrder.getWarehouseId();
        Long operatorId = SecurityUtil.getUserId();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalQuantity = BigDecimal.ZERO;

        // 遍历明细，扣减库存、写流水
        for (PurPurchaseReturnItem item : items) {
            BigDecimal quantity = item.getQuantity();
            BigDecimal price = item.getPrice();
            BigDecimal amount = quantity.multiply(price);
            String batchNo = item.getBatchNo() != null ? item.getBatchNo() : "";

            totalAmount = totalAmount.add(amount);
            totalQuantity = totalQuantity.add(quantity);

            // 查询库存记录
            InvInventory inventory = invInventoryMapper.selectOne(
                    new LambdaQueryWrapper<InvInventory>()
                            .eq(InvInventory::getWarehouseId, warehouseId)
                            .eq(InvInventory::getProductId, item.getProductId())
                            .eq(InvInventory::getBatchNo, batchNo)
            );

            BigDecimal beforeQuantity = BigDecimal.ZERO;
            if (inventory != null) {
                beforeQuantity = inventory.getQuantity();
                BigDecimal newQuantity = beforeQuantity.subtract(quantity);
                BigDecimal newTotalCost = inventory.getTotalCost().subtract(amount);
                if (newQuantity.compareTo(BigDecimal.ZERO) < 0) {
                    newQuantity = BigDecimal.ZERO;
                }
                if (newTotalCost.compareTo(BigDecimal.ZERO) < 0) {
                    newTotalCost = BigDecimal.ZERO;
                }
                inventory.setQuantity(newQuantity);
                inventory.setTotalCost(newTotalCost);
                invInventoryMapper.updateById(inventory);
            }

            // 写库存流水（采购退货出库，type=2）
            InvInventoryTransaction transaction = new InvInventoryTransaction();
            transaction.setWarehouseId(warehouseId);
            transaction.setProductId(item.getProductId());
            transaction.setBatchNo(batchNo);
            transaction.setTransactionType(2); // 采购退货出库
            transaction.setQuantity(quantity);
            transaction.setBeforeQuantity(beforeQuantity);
            transaction.setAfterQuantity(beforeQuantity.subtract(quantity));
            transaction.setCostPrice(price);
            transaction.setSourceType("purchase_return");
            transaction.setSourceId(returnOrder.getId());
            transaction.setSourceNo(returnOrder.getReturnNo());
            transaction.setTransactionTime(LocalDateTime.now());
            transaction.setOperatorId(operatorId);
            invInventoryTransactionMapper.insert(transaction);
        }

        // 更新退货单合计
        returnOrder.setTotalQuantity(totalQuantity);
        returnOrder.setTotalAmount(totalAmount);
        this.updateById(returnOrder);

        // 生成应付账款红冲记录（冲减应付）
        FinPayable payable = new FinPayable();
        payable.setSupplierId(returnOrder.getSupplierId());
        payable.setSourceType("purchase_return");
        payable.setSourceId(returnOrder.getId());
        payable.setSourceNo(returnOrder.getReturnNo());
        payable.setAmount(totalAmount.negate()); // 负数表示冲减
        payable.setPaidAmount(BigDecimal.ZERO);
        payable.setBalance(totalAmount.negate());
        payable.setStatus(0);
        finPayableMapper.insert(payable);
    }
}
