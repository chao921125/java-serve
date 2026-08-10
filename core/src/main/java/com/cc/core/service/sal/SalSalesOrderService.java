package com.cc.core.service.sal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.sal.SalesOrderQueryDTO;
import com.cc.core.dto.sal.SalesOrderSaveDTO;
import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.entity.sal.SalSalesOrderItem;

import java.util.List;

/**
 * 销售订单服务接口
 */
public interface SalSalesOrderService extends IService<SalSalesOrder> {

    /**
     * 分页查询销售订单
     */
    IPage<SalSalesOrder> page(SalesOrderQueryDTO query);

    /**
     * 创建销售订单（含明细），校验信用额度和库存可用量
     */
    void create(SalesOrderSaveDTO dto);

    /**
     * 更新销售订单（含明细，先删旧明细再插新明细）
     */
    void update(Long id, SalesOrderSaveDTO dto);

    /**
     * 审核销售订单：状态待审→已审，锁定库存
     */
    void approve(Long id);

    /**
     * 反审核销售订单
     */
    void reject(Long id);

    /**
     * 关闭销售订单
     */
    void close(Long id);

    /**
     * 挂单
     */
    void suspend(Long id);

    /**
     * 恢复挂单
     */
    void resume(Long id);

    /**
     * 获取挂单列表
     */
    java.util.List<com.cc.core.entity.sal.SalSalesOrder> getSuspended();

    /**
     * 获取销售订单明细列表
     */
    List<SalSalesOrderItem> getItems(Long orderId);
}
