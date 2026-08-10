package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 采购入库单主表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_purchase_receipts")
public class PurPurchaseReceipt extends BaseEntity {

    /** 入库单号 */
    private String receiptNo;

    /** 采购订单 ID */
    private Long orderId;

    /** 供应商 ID */
    private Long supplierId;

    /** 仓库 ID */
    private Long warehouseId;

    /** 入库日期 */
    private LocalDate receiptDate;

    /** 总数量 */
    private BigDecimal totalQuantity;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 状态 0-草稿 1-待审核 2-已审核 3-已完成 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;
}
