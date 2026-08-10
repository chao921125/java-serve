package com.cc.core.dto.pur;

import lombok.Data;

import java.time.LocalDate;

/**
 * 采购订单查询条件
 */
@Data
public class PurchaseOrderQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 订单编号 */
    private String orderNo;

    /** 供应商 ID */
    private Long supplierId;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 状态 */
    private Integer status;
}
