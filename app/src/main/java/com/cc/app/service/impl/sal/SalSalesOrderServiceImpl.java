package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.sal.SalesOrderItemDTO;
import com.cc.core.dto.sal.SalesOrderQueryDTO;
import com.cc.core.dto.sal.SalesOrderSaveDTO;
import com.cc.core.entity.bas.BasCustomer;
import com.cc.core.entity.fin.FinReceivable;
import com.cc.core.entity.inv.InvInventory;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.entity.sal.SalSalesOrderItem;
import com.cc.core.mapper.bas.BasCustomerMapper;
import com.cc.core.mapper.fin.FinReceivableMapper;
import com.cc.core.mapper.inv.InvInventoryMapper;
import com.cc.core.mapper.sal.SalSalesOrderMapper;
import com.cc.core.service.sal.SalSalesOrderItemService;
import com.cc.core.service.sal.SalSalesOrderService;
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
 * 销售订单服务实现
 */
@Service
@RequiredArgsConstructor
public class SalSalesOrderServiceImpl extends ServiceImpl<SalSalesOrderMapper, SalSalesOrder>
        implements SalSalesOrderService {

    private final SalSalesOrderItemService salesOrderItemService;
    private final InvInventoryMapper invInventoryMapper;
    private final BasCustomerMapper basCustomerMapper;
    private final FinReceivableMapper finReceivableMapper;

    @Override
    public IPage<SalSalesOrder> page(SalesOrderQueryDTO query) {
        Page<SalSalesOrder> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<SalSalesOrder> wrapper = new LambdaQueryWrapper<>();
        if (query.getOrderNo() != null && !query.getOrderNo().isEmpty()) {
            wrapper.like(SalSalesOrder::getOrderNo, query.getOrderNo());
        }
        if (query.getCustomerId() != null) {
            wrapper.eq(SalSalesOrder::getCustomerId, query.getCustomerId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(SalSalesOrder::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(SalSalesOrder::getOrderDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(SalSalesOrder::getOrderDate, query.getEndDate());
        }
        wrapper.orderByDesc(SalSalesOrder::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SalesOrderSaveDTO dto) {
        // 构建主表
        SalSalesOrder order = new SalSalesOrder();
        order.setOrderNo(generateOrderNo());
        order.setCustomerId(dto.getCustomerId());
        order.setWarehouseId(dto.getWarehouseId());
        order.setOrderDate(dto.getOrderDate());
        order.setExpectedDate(dto.getExpectedDate());
        order.setDeliveredQuantity(BigDecimal.ZERO);
        order.setStatus(0); // 草稿
        order.setRemark(dto.getRemark());

        // 计算合计并构建明细
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalReceivable = BigDecimal.ZERO;

        // 先保存主表获取 ID
        this.save(order);

        for (int i = 0; i < dto.getItems().size(); i++) {
            SalesOrderItemDTO itemDTO = dto.getItems().get(i);
            SalSalesOrderItem item = new SalSalesOrderItem();
            item.setOrderId(order.getId());
            item.setProductId(itemDTO.getProductId());
            item.setUnitId(itemDTO.getUnitId());
            item.setQuantity(itemDTO.getQuantity());
            item.setDeliveredQuantity(BigDecimal.ZERO);
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
            totalReceivable = totalReceivable.add(itemTotal);

            salesOrderItemService.save(item);
        }

        // 更新主表合计
        order.setTotalQuantity(totalQuantity);
        order.setTotalAmount(totalAmount);
        order.setTotalTax(totalTax);
        order.setTotalReceivable(totalReceivable);
        this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, SalesOrderSaveDTO dto) {
        SalSalesOrder order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("销售订单不存在");
        }
        if (order.getStatus() != 0) {
            throw new RuntimeException("只有草稿状态的订单才能修改");
        }

        // 更新主表字段
        order.setCustomerId(dto.getCustomerId());
        order.setWarehouseId(dto.getWarehouseId());
        order.setOrderDate(dto.getOrderDate());
        order.setExpectedDate(dto.getExpectedDate());
        order.setRemark(dto.getRemark());

        // 先删除旧明细
        salesOrderItemService.remove(
                new LambdaQueryWrapper<SalSalesOrderItem>()
                        .eq(SalSalesOrderItem::getOrderId, id)
        );

        // 重新计算并保存明细
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalReceivable = BigDecimal.ZERO;

        for (int i = 0; i < dto.getItems().size(); i++) {
            SalesOrderItemDTO itemDTO = dto.getItems().get(i);
            SalSalesOrderItem item = new SalSalesOrderItem();
            item.setOrderId(id);
            item.setProductId(itemDTO.getProductId());
            item.setUnitId(itemDTO.getUnitId());
            item.setQuantity(itemDTO.getQuantity());
            item.setDeliveredQuantity(BigDecimal.ZERO);
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
            totalReceivable = totalReceivable.add(itemTotal);

            salesOrderItemService.save(item);
        }

        order.setTotalQuantity(totalQuantity);
        order.setTotalAmount(totalAmount);
        order.setTotalTax(totalTax);
        order.setTotalReceivable(totalReceivable);
        this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        SalSalesOrder order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("销售订单不存在");
        }
        if (order.getStatus() != 1) {
            throw new RuntimeException("只有待审核状态的订单才能审核");
        }

        // 校验信用额度：查询该客户未核销的应收账款总额 + 本单金额 是否超过信用额度
        BasCustomer customer = basCustomerMapper.selectById(order.getCustomerId());
        if (customer != null && customer.getCreditLimit() != null
                && customer.getCreditLimit().compareTo(BigDecimal.ZERO) > 0) {
            // 查询该客户未核销的应收账款余额总额
            List<FinReceivable> receivables = finReceivableMapper.selectList(
                    new LambdaQueryWrapper<FinReceivable>()
                            .eq(FinReceivable::getCustomerId, order.getCustomerId())
                            .in(FinReceivable::getStatus, 0, 1)
            );
            BigDecimal outstandingBalance = BigDecimal.ZERO;
            for (FinReceivable r : receivables) {
                outstandingBalance = outstandingBalance.add(r.getBalance());
            }
            // 未核销应收 + 本单应收总额
            BigDecimal totalExposure = outstandingBalance.add(order.getTotalReceivable());
            if (totalExposure.compareTo(customer.getCreditLimit()) > 0) {
                throw new RuntimeException("信用额度不足：已用 " + outstandingBalance + " + 本单 " + order.getTotalReceivable()
                        + " = " + totalExposure + "，超过信用额度 " + customer.getCreditLimit());
            }
        }

        // 校验库存可用量
        List<SalSalesOrderItem> items = salesOrderItemService.list(
                new LambdaQueryWrapper<SalSalesOrderItem>().eq(SalSalesOrderItem::getOrderId, id)
        );
        for (SalSalesOrderItem item : items) {
            InvInventory inventory = invInventoryMapper.selectOne(
                    new LambdaQueryWrapper<InvInventory>()
                            .eq(InvInventory::getWarehouseId, order.getWarehouseId())
                            .eq(InvInventory::getProductId, item.getProductId())
            );
            BigDecimal available = BigDecimal.ZERO;
            if (inventory != null) {
                BigDecimal qty = inventory.getQuantity() != null ? inventory.getQuantity() : BigDecimal.ZERO;
                BigDecimal locked = inventory.getLockedQuantity() != null ? inventory.getLockedQuantity() : BigDecimal.ZERO;
                available = qty.subtract(locked);
            }
            if (available.compareTo(item.getQuantity()) < 0) {
                throw new RuntimeException("商品库存不足：可用量 " + available + "，订单数量 " + item.getQuantity());
            }
        }

        // 审核通过：状态待审→已审，锁定库存
        order.setStatus(2); // 已审核
        order.setApproverId(SecurityUtil.getUserId());
        order.setApproveTime(LocalDateTime.now());
        this.updateById(order);

        // 锁定库存：locked_quantity 增加
        for (SalSalesOrderItem item : items) {
            InvInventory inventory = invInventoryMapper.selectOne(
                    new LambdaQueryWrapper<InvInventory>()
                            .eq(InvInventory::getWarehouseId, order.getWarehouseId())
                            .eq(InvInventory::getProductId, item.getProductId())
            );
            if (inventory != null) {
                BigDecimal currentLocked = inventory.getLockedQuantity() != null
                        ? inventory.getLockedQuantity() : BigDecimal.ZERO;
                inventory.setLockedQuantity(currentLocked.add(item.getQuantity()));
                invInventoryMapper.updateById(inventory);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(Long id) {
        SalSalesOrder order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("销售订单不存在");
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
        SalSalesOrder order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("销售订单不存在");
        }
        if (order.getStatus() != 2 && order.getStatus() != 3) {
            throw new RuntimeException("只有已审核或部分发货状态的订单才能关闭");
        }

        // 关闭时释放未发货部分锁定的库存
        List<SalSalesOrderItem> items = salesOrderItemService.list(
                new LambdaQueryWrapper<SalSalesOrderItem>().eq(SalSalesOrderItem::getOrderId, id)
        );
        for (SalSalesOrderItem item : items) {
            BigDecimal undelivered = item.getQuantity().subtract(
                    item.getDeliveredQuantity() != null ? item.getDeliveredQuantity() : BigDecimal.ZERO);
            if (undelivered.compareTo(BigDecimal.ZERO) > 0) {
                InvInventory inventory = invInventoryMapper.selectOne(
                        new LambdaQueryWrapper<InvInventory>()
                                .eq(InvInventory::getWarehouseId, order.getWarehouseId())
                                .eq(InvInventory::getProductId, item.getProductId())
                );
                if (inventory != null) {
                    BigDecimal currentLocked = inventory.getLockedQuantity() != null
                            ? inventory.getLockedQuantity() : BigDecimal.ZERO;
                    BigDecimal newLocked = currentLocked.subtract(undelivered);
                    if (newLocked.compareTo(BigDecimal.ZERO) < 0) {
                        newLocked = BigDecimal.ZERO;
                    }
                    inventory.setLockedQuantity(newLocked);
                    invInventoryMapper.updateById(inventory);
                }
            }
        }

        order.setStatus(5); // 已关闭
        this.updateById(order);
    }

    @Override
    public List<SalSalesOrderItem> getItems(Long orderId) {
        return salesOrderItemService.list(
                new LambdaQueryWrapper<SalSalesOrderItem>()
                        .eq(SalSalesOrderItem::getOrderId, orderId)
                        .orderByAsc(SalSalesOrderItem::getSort)
        );
    }

    @Override
    public void suspend(Long id) {
        SalSalesOrder order = this.getById(id);
        if (order == null) throw new RuntimeException("销售订单不存在");
        order.setIsSuspended(1);
        this.updateById(order);
    }

    @Override
    public void resume(Long id) {
        SalSalesOrder order = this.getById(id);
        if (order == null) throw new RuntimeException("销售订单不存在");
        order.setIsSuspended(0);
        order.setStatus(0);
        this.updateById(order);
    }

    @Override
    public List<SalSalesOrder> getSuspended() {
        return this.list(new LambdaQueryWrapper<SalSalesOrder>()
                .eq(SalSalesOrder::getIsSuspended, 1));
    }

    /**
     * 生成销售订单编号：SO + yyyyMMddHHmmss + 4位随机数
     */
    private String generateOrderNo() {
        return "SO" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ThreadLocalRandom.current().nextInt(1000, 9999);
    }
}
