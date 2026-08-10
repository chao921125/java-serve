package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 运营费用分摊
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fin_expense_allocation")
public class FinExpenseAllocation extends BaseEntity {

    /** 分摊单号 */
    private String expenseNo;

    /** 费用类型: SHIPPING/PACKAGING/ADVERTISING/RENT/UTILITIES/SALARY/OTHER */
    private String expenseType;

    /** 费用金额 */
    private BigDecimal expenseAmount;

    /** 分摊方式: BY_SALES_AMOUNT/BY_SALES_QUANTITY/BY_COST/BY_WEIGHT/MANUAL */
    private String allocationMethod;

    /** 分摊周期 YYYY-MM */
    private String allocationPeriod;

    /** 状态: 0-待分摊 1-已分摊 2-已冲销 */
    private Integer status;

    /** 已分摊金额 */
    private BigDecimal allocatedAmount;
}
