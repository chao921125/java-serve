package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 运营费用分摊明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fin_expense_allocation_detail")
public class FinExpenseAllocationDetail extends BaseEntity {

    /** 分摊单ID */
    private Long allocationId;

    /** 分摊对象: PRODUCT/SALE_ORDER/DEPARTMENT */
    private String targetType;

    /** 对象ID */
    private Long targetId;

    /** 分摊金额 */
    private BigDecimal amount;

    /** 分摊基数 */
    private BigDecimal allocationBase;
}
