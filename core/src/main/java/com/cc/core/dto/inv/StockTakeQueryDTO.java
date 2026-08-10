package com.cc.core.dto.inv;

import lombok.Data;

import java.time.LocalDate;

/**
 * 盘点单查询条件
 */
@Data
public class StockTakeQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 盘点单号 */
    private String takeNo;

    /** 仓库 ID */
    private Long warehouseId;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 状态 */
    private Integer status;
}
