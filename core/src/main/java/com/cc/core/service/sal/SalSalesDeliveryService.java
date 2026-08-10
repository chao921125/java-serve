package com.cc.core.service.sal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.sal.SalesDeliveryQueryDTO;
import com.cc.core.entity.sal.SalSalesDelivery;
import com.cc.core.entity.sal.SalSalesDeliveryItem;

import java.util.List;

/**
 * 销售出库单服务接口
 */
public interface SalSalesDeliveryService extends IService<SalSalesDelivery> {

    /**
     * 分页查询销售出库单
     */
    IPage<SalSalesDelivery> page(SalesDeliveryQueryDTO query);

    /**
     * 获取出库单明细列表
     */
    List<SalSalesDeliveryItem> getItems(Long deliveryId);

    /**
     * 审核出库单：扣减库存和锁定数量、写库存流水、生成应收账款、更新销售订单已发货数量
     */
    void approve(Long id);
}
