package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sal.SalQuotation;
import com.cc.core.mapper.sal.SalQuotationMapper;
import com.cc.core.service.sal.SalQuotationService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import com.cc.core.service.sal.SalQuotationItemService;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.core.service.sal.SalSalesOrderItemService;
import com.cc.core.entity.sal.SalQuotationItem;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.entity.sal.SalSalesOrderItem;

/**
 * SalQuotation 服务实现
 */
@Service
@RequiredArgsConstructor
public class SalQuotationServiceImpl extends ServiceImpl<SalQuotationMapper, SalQuotation> implements SalQuotationService {
    private final SalQuotationItemService quotationItemService;
    private final SalSalesOrderService salesOrderService;
    private final SalSalesOrderItemService salesOrderItemService;


    // ==== Business Logic Methods ====

    @Override
    public void issue(Long id) {
        com.cc.core.entity.sal.SalQuotation entity = getById(id);
        if (entity.getStatus() != 0) throw new RuntimeException("仅草稿状态可发出");
        entity.setStatus(1);
        updateById(entity);
    }

    @Override
    public void confirm(Long id) {
        com.cc.core.entity.sal.SalQuotation entity = getById(id);
        if (entity.getStatus() != 1) throw new RuntimeException("仅已发出状态可确认");
        entity.setStatus(2);
        updateById(entity);
    }

    @Override
    public Long convertToOrder(Long id) {
        com.cc.core.entity.sal.SalQuotation entity = getById(id);
        if (entity.getStatus() != 2) throw new RuntimeException("仅已确认状态可转订单");
        entity.setStatus(3);
        updateById(entity);
        // 创建销售订单
        com.cc.core.entity.sal.SalSalesOrder order = new com.cc.core.entity.sal.SalSalesOrder();
        order.setOrderNo("SO-" + System.currentTimeMillis());
        order.setCustomerId(entity.getCustomerId());
        order.setOrderDate(java.time.LocalDate.now());
        order.setTotalAmount(entity.getTotalAmount());
        order.setStatus(0);
        salesOrderService.save(order);
        // 复制明细
        java.util.List<com.cc.core.entity.sal.SalQuotationItem> items = quotationItemService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.cc.core.entity.sal.SalQuotationItem>()
                .eq(com.cc.core.entity.sal.SalQuotationItem::getQuotationId, id)
        );
        for (com.cc.core.entity.sal.SalQuotationItem item : items) {
            com.cc.core.entity.sal.SalSalesOrderItem orderItem = new com.cc.core.entity.sal.SalSalesOrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getUnitPrice());
            orderItem.setTotalAmount(item.getTotalAmount());
            salesOrderItemService.save(orderItem);
        }
        return order.getId();
    }

    @Override
    public void expire(Long id) {
        com.cc.core.entity.sal.SalQuotation entity = getById(id);
        entity.setStatus(4);
        updateById(entity);
    }

}
