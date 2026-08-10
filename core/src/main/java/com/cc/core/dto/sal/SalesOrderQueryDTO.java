package com.cc.core.dto.sal;

import lombok.Data;

import java.time.LocalDate;

/**
 * 销售订单查询条件
 */
@Data
public class SalesOrderQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 订单编号 */
    private String orderNo;

    /** 客户 ID */
    private Long customerId;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 状态 */
    private Integer status;
}
