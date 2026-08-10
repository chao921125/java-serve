package com.cc.core.dto.fin;

import lombok.Data;

import java.time.LocalDate;

/**
 * 费用支出查询条件
 */
@Data
public class ExpenseQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 费用单号 */
    private String expenseNo;

    /** 部门 ID */
    private Long departmentId;

    /** 起始日期 */
    private LocalDate startDate;

    /** 结束日期 */
    private LocalDate endDate;

    /** 状态 */
    private Integer status;
}
