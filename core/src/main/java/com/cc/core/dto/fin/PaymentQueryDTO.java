package com.cc.core.dto.fin;

import lombok.Data;

import java.time.LocalDate;

/**
 * 付款单查询条件
 */
@Data
public class PaymentQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 付款单号 */
    private String paymentNo;

    /** 供应商 ID */
    private Long supplierId;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 状态 */
    private Integer status;
}
