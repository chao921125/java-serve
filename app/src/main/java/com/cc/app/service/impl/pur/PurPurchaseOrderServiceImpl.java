package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.pur.PurchaseOrderItemDTO;
import com.cc.core.dto.pur.PurchaseOrderQueryDTO;
import com.cc.core.dto.pur.PurchaseOrderSaveDTO;
import com.cc.core.entity.pur.PurPurchaseOrder;
import com.cc.core.entity.pur.PurPurchaseOrderItem;
import com.cc.core.mapper.pur.PurPurchaseOrderMapper;
import com.cc.core.service.pur.PurPurchaseOrderItemService;
import com.cc.core.service.pur.PurPurchaseOrderService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 采购订单服务实现
 */
@Service
@RequiredArgsConstructor
public class PurPurchaseOrderServiceImpl extends ServiceImpl<PurPurchaseOrderMapper, PurPurchaseOrder>
        implements PurPurchaseOrderService {

    private final PurPurchaseOrderItemService purchaseOrderItemService;

    @Override
    public IPage<PurPurchaseOrder> page(PurchaseOrderQueryDTO query) {
        Page<PurPurchaseOrder> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<PurPurchaseOrder> wrapper = new LambdaQueryWrapper<>();
        if (query.getOrderNo() != null && !query.getOrderNo().isEmpty()) {
            wrapper.like(PurPurchaseOrder::getOrderNo, query.getOrderNo());
        }
        if (query.getSupplierId() != null) {
            wrapper.eq(PurPurchaseOrder::getSupplierId, query.getSupplierId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(PurPurchaseOrder::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(PurPurchaseOrder::getOrderDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(PurPurchaseOrder::getOrderDate, query.getEndDate());
        }
        wrapper.orderByDesc(PurPurchaseOrder::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PurchaseOrderSaveDTO dto) {
        // 构建主表
        PurPurchaseOrder order = new PurPurchaseOrder();
        order.setOrderNo(generateOrderNo());
        order.setSupplierId(dto.getSupplierId());
        order.setWarehouseId(dto.getWarehouseId());
        order.setOrderDate(dto.getOrderDate());
        order.setExpectedDate(dto.getExpectedDate());
        order.setReceivedQuantity(BigDecimal.ZERO);
        order.setStatus(0); // 草稿
        order.setRemark(dto.getRemark());

        // 计算合计并构建明细
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalPayable = BigDecimal.ZERO;

        // 先保存主表获取 ID
        this.save(order);

        for (int i = 0; i < dto.getItems().size(); i++) {
            PurchaseOrderItemDTO itemDTO = dto.getItems().get(i);
            PurPurchaseOrderItem item = new PurPurchaseOrderItem();
            item.setOrderId(order.getId());
            item.setProductId(itemDTO.getProductId());
            item.setUnitId(itemDTO.getUnitId());
            item.setQuantity(itemDTO.getQuantity());
            item.setReceivedQuantity(BigDecimal.ZERO);
            item.setPrice(itemDTO.getPrice());
            item.setTaxRate(itemDTO.getTaxRate() != null ? itemDTO.getTaxRate() : BigDecimal.ZERO);
            item.setRemark(itemDTO.getRemark());
            item.setSort(itemDTO.getSort() != null ? itemDTO.getSort() : i);

            // 计算金额：price 为含税单价
            // amount = price * quantity / (1 + taxRate)
            // taxAmount = price * quantity - amount
            // totalAmount = price * quantity
            BigDecimal quantity = itemDTO.getQuantity();
            BigDecimal price = itemDTO.getPrice();
            BigDecimal taxRate = item.getTaxRate();
            BigDecimal itemTotal = price.multiply(quantity);
            BigDecimal itemAmount;
            BigDecimal itemTaxAmount;
            if (taxRate.compareTo(BigDecimal.ZERO) > 0) {
                itemAmount = itemTotal.divide(BigDecimal.ONE.add(taxRate), 4, RoundingMode.HALF_UP);
                itemTaxAmount = itemTotal.subtract(itemAmount);
            } else {
                itemAmount = itemTotal;
                itemTaxAmount = BigDecimal.ZERO;
            }
            item.setAmount(itemAmount);
            item.setTaxAmount(itemTaxAmount);
            item.setTotalAmount(itemTotal);

            totalQuantity = totalQuantity.add(quantity);
            totalAmount = totalAmount.add(itemAmount);
            totalTax = totalTax.add(itemTaxAmount);
            totalPayable = totalPayable.add(itemTotal);

            purchaseOrderItemService.save(item);
        }

        // 更新主表合计
        order.setTotalQuantity(totalQuantity);
        order.setTotalAmount(totalAmount);
        order.setTotalTax(totalTax);
        order.setTotalPayable(totalPayable);
        this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, PurchaseOrderSaveDTO dto) {
        PurPurchaseOrder order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("采购订单不存在");
        }
        if (order.getStatus() != 0) {
            throw new RuntimeException("只有草稿状态的订单才能修改");
        }

        // 更新主表字段
        order.setSupplierId(dto.getSupplierId());
        order.setWarehouseId(dto.getWarehouseId());
        order.setOrderDate(dto.getOrderDate());
        order.setExpectedDate(dto.getExpectedDate());
        order.setRemark(dto.getRemark());

        // 先删除旧明细
        purchaseOrderItemService.remove(
                new LambdaQueryWrapper<PurPurchaseOrderItem>()
                        .eq(PurPurchaseOrderItem::getOrderId, id)
        );

        // 重新计算并保存明细
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalPayable = BigDecimal.ZERO;

        for (int i = 0; i < dto.getItems().size(); i++) {
            PurchaseOrderItemDTO itemDTO = dto.getItems().get(i);
            PurPurchaseOrderItem item = new PurPurchaseOrderItem();
            item.setOrderId(id);
            item.setProductId(itemDTO.getProductId());
            item.setUnitId(itemDTO.getUnitId());
            item.setQuantity(itemDTO.getQuantity());
            item.setReceivedQuantity(BigDecimal.ZERO);
            item.setPrice(itemDTO.getPrice());
            item.setTaxRate(itemDTO.getTaxRate() != null ? itemDTO.getTaxRate() : BigDecimal.ZERO);
            item.setRemark(itemDTO.getRemark());
            item.setSort(itemDTO.getSort() != null ? itemDTO.getSort() : i);

            BigDecimal quantity = itemDTO.getQuantity();
            BigDecimal price = itemDTO.getPrice();
            BigDecimal taxRate = item.getTaxRate();
            BigDecimal itemTotal = price.multiply(quantity);
            BigDecimal itemAmount;
            BigDecimal itemTaxAmount;
            if (taxRate.compareTo(BigDecimal.ZERO) > 0) {
                itemAmount = itemTotal.divide(BigDecimal.ONE.add(taxRate), 4, RoundingMode.HALF_UP);
                itemTaxAmount = itemTotal.subtract(itemAmount);
            } else {
                itemAmount = itemTotal;
                itemTaxAmount = BigDecimal.ZERO;
            }
            item.setAmount(itemAmount);
            item.setTaxAmount(itemTaxAmount);
            item.setTotalAmount(itemTotal);

            totalQuantity = totalQuantity.add(quantity);
            totalAmount = totalAmount.add(itemAmount);
            totalTax = totalTax.add(itemTaxAmount);
            totalPayable = totalPayable.add(itemTotal);

            purchaseOrderItemService.save(item);
        }

        order.setTotalQuantity(totalQuantity);
        order.setTotalAmount(totalAmount);
        order.setTotalTax(totalTax);
        order.setTotalPayable(totalPayable);
        this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        PurPurchaseOrder order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("采购订单不存在");
        }
        if (order.getStatus() != 1) {
            throw new RuntimeException("只有待审核状态的订单才能审核");
        }
        order.setStatus(2); // 已审核
        order.setApproverId(SecurityUtil.getUserId());
        order.setApproveTime(LocalDateTime.now());
        this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(Long id) {
        PurPurchaseOrder order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("采购订单不存在");
        }
        if (order.getStatus() != 1) {
            throw new RuntimeException("只有待审核状态的订单才能反审核");
        }
        order.setStatus(0); // 草稿
        order.setApproverId(null);
        order.setApproveTime(null);
        this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void close(Long id) {
        PurPurchaseOrder order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("采购订单不存在");
        }
        if (order.getStatus() != 2 && order.getStatus() != 3) {
            throw new RuntimeException("只有已审核或部分到货状态的订单才能关闭");
        }
        order.setStatus(5); // 已关闭
        this.updateById(order);
    }

    @Override
    public List<PurPurchaseOrderItem> getItems(Long orderId) {
        return purchaseOrderItemService.list(
                new LambdaQueryWrapper<PurPurchaseOrderItem>()
                        .eq(PurPurchaseOrderItem::getOrderId, orderId)
                        .orderByAsc(PurPurchaseOrderItem::getSort)
        );
    }

    /**
     * 生成采购订单编号：PO + yyyyMMddHHmmss + 4位随机数
     */
    private String generateOrderNo() {
        return "PO" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ThreadLocalRandom.current().nextInt(1000, 9999);
    }
}
