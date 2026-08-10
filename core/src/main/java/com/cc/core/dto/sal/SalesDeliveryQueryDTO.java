package com.cc.core.dto.sal;

import lombok.Data;

import java.time.LocalDate;

/**
 * 销售出库单查询条件
 */
@Data
public class SalesDeliveryQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 出库单号 */
    private String deliveryNo;

    /** 客户 ID */
    private Long customerId;

    /** 销售订单 ID */
    private Long orderId;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 状态 */
    private Integer status;
}
