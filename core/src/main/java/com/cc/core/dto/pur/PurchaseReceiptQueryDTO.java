package com.cc.core.dto.pur;

import lombok.Data;

import java.time.LocalDate;

/**
 * 采购入库单查询条件
 */
@Data
public class PurchaseReceiptQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 入库单号 */
    private String receiptNo;

    /** 供应商 ID */
    private Long supplierId;

    /** 采购订单 ID */
    private Long orderId;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 状态 */
    private Integer status;
}
