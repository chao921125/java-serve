package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 费用支出
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fin_expenses")
public class FinExpense extends BaseEntity {

    /** 费用单号 */
    private String expenseNo;

    /** 部门 ID */
    private Long departmentId;

    /** 费用类别 */
    private String category;

    /** 金额 */
    private BigDecimal amount;

    /** 费用日期 */
    private LocalDate expenseDate;

    /** 账户 ID */
    private Long accountId;

    /** 状态 0-草稿 1-待审 2-已审 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;
}
