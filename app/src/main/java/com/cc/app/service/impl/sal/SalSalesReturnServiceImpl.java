package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.sal.SalesReturnQueryDTO;
import com.cc.core.entity.fin.FinReceivable;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.inv.InvInventoryTransaction;
import com.cc.core.entity.sal.SalSalesReturn;
import com.cc.core.entity.sal.SalSalesReturnItem;
import com.cc.core.mapper.fin.FinReceivableMapper;
import com.cc.core.mapper.inv.InvInventoryMapper;
import com.cc.core.mapper.inv.InvInventoryTransactionMapper;
import com.cc.core.mapper.sal.SalSalesReturnMapper;
import com.cc.core.service.sal.SalSalesReturnItemService;
import com.cc.core.service.sal.SalSalesReturnService;
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
 * 销售退货单服务实现
 */
@Service
@RequiredArgsConstructor
public class SalSalesReturnServiceImpl extends ServiceImpl<SalSalesReturnMapper, SalSalesReturn>
        implements SalSalesReturnService {

    private final SalSalesReturnItemService salesReturnItemService;
    private final InvInventoryMapper invInventoryMapper;
    private final InvInventoryTransactionMapper invInventoryTransactionMapper;
    private final FinReceivableMapper finReceivableMapper;

    @Override
    public IPage<SalSalesReturn> page(SalesReturnQueryDTO query) {
        Page<SalSalesReturn> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<SalSalesReturn> wrapper = new LambdaQueryWrapper<>();
        if (query.getReturnNo() != null && !query.getReturnNo().isEmpty()) {
            wrapper.like(SalSalesReturn::getReturnNo, query.getReturnNo());
        }
        if (query.getCustomerId() != null) {
            wrapper.eq(SalSalesReturn::getCustomerId, query.getCustomerId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(SalSalesReturn::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(SalSalesReturn::getReturnDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(SalSalesReturn::getReturnDate, query.getEndDate());
        }
        wrapper.orderByDesc(SalSalesReturn::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<SalSalesReturnItem> getItems(Long returnId) {
        return salesReturnItemService.list(
                new LambdaQueryWrapper<SalSalesReturnItem>()
                        .eq(SalSalesReturnItem::getReturnId, returnId)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        SalSalesReturn returnOrder = this.getById(id);
        if (returnOrder == null) {
            throw new RuntimeException("销售退货单不存在");
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
        List<SalSalesReturnItem> items = this.getItems(id);
        if (items.isEmpty()) {
            throw new RuntimeException("退货单明细为空");
        }

        Long warehouseId = returnOrder.getWarehouseId();
        Long operatorId = SecurityUtil.getUserId();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalQuantity = BigDecimal.ZERO;

        // 遍历明细，库存回库、写流水
        for (SalSalesReturnItem item : items) {
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
            BigDecimal costPrice = price;

            if (inventory == null) {
                // 新增库存记录（退货回库）
                inventory = new InvInventory();
                inventory.setWarehouseId(warehouseId);
                inventory.setProductId(item.getProductId());
                inventory.setBatchNo(batchNo);
                inventory.setQuantity(quantity);
                inventory.setLockedQuantity(BigDecimal.ZERO);
                inventory.setCostPrice(price);
                inventory.setTotalCost(amount);
                beforeQuantity = BigDecimal.ZERO;
                invInventoryMapper.insert(inventory);
            } else {
                // 增加库存数量
                beforeQuantity = inventory.getQuantity() != null ? inventory.getQuantity() : BigDecimal.ZERO;
                BigDecimal oldTotalCost = inventory.getTotalCost() != null
                        ? inventory.getTotalCost() : BigDecimal.ZERO;
                BigDecimal newQuantity = beforeQuantity.add(quantity);
                BigDecimal newTotalCost = oldTotalCost.add(amount);
                costPrice = newQuantity.compareTo(BigDecimal.ZERO) > 0
                        ? newTotalCost.divide(newQuantity, 4, RoundingMode.HALF_UP)
                        : price;

                inventory.setQuantity(newQuantity);
                inventory.setTotalCost(newTotalCost);
                inventory.setCostPrice(costPrice);
                invInventoryMapper.updateById(inventory);
            }

            // 写库存流水（销售退货入库，type=4）
            InvInventoryTransaction transaction = new InvInventoryTransaction();
            transaction.setWarehouseId(warehouseId);
            transaction.setProductId(item.getProductId());
            transaction.setBatchNo(batchNo);
            transaction.setTransactionType(4); // 销售退货入库
            transaction.setQuantity(quantity);
            transaction.setBeforeQuantity(beforeQuantity);
            transaction.setAfterQuantity(beforeQuantity.add(quantity));
            transaction.setCostPrice(costPrice);
            transaction.setSourceType("sales_return");
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

        // 冲减应收账款（红冲记录）
        FinReceivable receivable = new FinReceivable();
        receivable.setCustomerId(returnOrder.getCustomerId());
        receivable.setSourceType("sales_return");
        receivable.setSourceId(returnOrder.getId());
        receivable.setSourceNo(returnOrder.getReturnNo());
        receivable.setAmount(totalAmount.negate()); // 负数表示冲减
        receivable.setReceivedAmount(BigDecimal.ZERO);
        receivable.setBalance(totalAmount.negate());
        receivable.setStatus(0);
        finReceivableMapper.insert(receivable);
    }

    /**
     * 生成退货单编号：SR + yyyyMMddHHmmss + 4位随机数
     */
    public static String generateReturnNo() {
        return "SR" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ThreadLocalRandom.current().nextInt(1000, 9999);
    }
}
