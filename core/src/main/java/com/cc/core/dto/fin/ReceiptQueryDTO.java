package com.cc.core.dto.fin;

import lombok.Data;

import java.time.LocalDate;

/**
 * 收款单查询条件
 */
@Data
public class ReceiptQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 收款单号 */
    private String receiptNo;

    /** 客户 ID */
    private Long customerId;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 状态 */
    private Integer status;
}
