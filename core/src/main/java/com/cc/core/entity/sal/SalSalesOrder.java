package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 销售订单主表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_sales_orders")
public class SalSalesOrder extends BaseEntity {

    /** 订单编号 */
    private String orderNo;

    /** 客户 ID */
    private Long customerId;

    /** 仓库 ID */
    private Long warehouseId;

    /** 订单日期 */
    private LocalDate orderDate;

    /** 预计交货日期 */
    private LocalDate expectedDate;

    /** 总数量 */
    private BigDecimal totalQuantity;

    /** 总金额（不含税） */
    private BigDecimal totalAmount;

    /** 总税额 */
    private BigDecimal totalTax;

    /** 应收总额（含税） */
    private BigDecimal totalReceivable;

    /** 已发货数量 */
    private BigDecimal deliveredQuantity;

    /** 状态 0-草稿 1-待审核 2-已审核 3-部分发货 4-已完成 5-已关闭 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;

    /** 是否挂单 0-否 1-是 */
    private Integer isSuspended;
}
