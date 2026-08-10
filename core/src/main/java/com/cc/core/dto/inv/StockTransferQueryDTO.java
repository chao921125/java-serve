package com.cc.core.dto.inv;

import lombok.Data;

import java.time.LocalDate;

/**
 * 调拨单查询条件
 */
@Data
public class StockTransferQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 调拨单号 */
    private String transferNo;

    /** 调出仓库 ID */
    private Long fromWarehouseId;

    /** 调入仓库 ID */
    private Long toWarehouseId;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 状态 */
    private Integer status;
}
