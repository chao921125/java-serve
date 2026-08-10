package com.cc.core.dto.fin;

import lombok.Data;

import java.time.LocalDate;

/**
 * 应付账款查询条件
 */
@Data
public class PayableQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 供应商 ID */
    private Long supplierId;

    /** 状态 */
    private Integer status;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;
}
