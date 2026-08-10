package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 其他收支
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fin_other_incomes")
public class FinOtherIncome extends BaseEntity {

    /** 类型 0-收入 1-支出 */
    private Integer type;

    /** 类别 */
    private String category;

    /** 金额 */
    private BigDecimal amount;

    /** 发生日期 */
    private LocalDate occurDate;

    /** 账户 ID */
    private Long accountId;

    /** 状态 0-草稿 1-待审 2-已审 */
    private Integer status;
}
