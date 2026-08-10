package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 采购订单主表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_purchase_orders")
public class PurPurchaseOrder extends BaseEntity {

    /** 订单编号 */
    private String orderNo;

    /** 供应商 ID */
    private Long supplierId;

    /** 仓库 ID */
    private Long warehouseId;

    /** 订单日期 */
    private LocalDate orderDate;

    /** 预计到货日期 */
    private LocalDate expectedDate;

    /** 总数量 */
    private BigDecimal totalQuantity;

    /** 总金额（不含税） */
    private BigDecimal totalAmount;

    /** 总税额 */
    private BigDecimal totalTax;

    /** 应付总额（含税） */
    private BigDecimal totalPayable;

    /** 已收数量 */
    private BigDecimal receivedQuantity;

    /** 状态 0-草稿 1-待审核 2-已审核 3-部分到货 4-已完成 5-已关闭 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;
}
