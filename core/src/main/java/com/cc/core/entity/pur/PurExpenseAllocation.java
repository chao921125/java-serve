package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 采购费用分摊
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_expense_allocation")
public class PurExpenseAllocation extends BaseEntity {

    /** 分摊单号 */
    private String allocationNo;

    /** 关联入库单ID */
    private Long receiptId;

    /** 费用类型: FREIGHT-运费, TARIFF-关税, HANDLING-装卸, INSURANCE-保险, OTHER */
    private String expenseType;

    /** 费用金额 */
    private BigDecimal expenseAmount;

    /** 分摊方式: BY_AMOUNT-按金额, BY_QUANTITY-按数量, BY_WEIGHT-按重量, BY_VOLUME-按体积, MANUAL */
    private String allocationMethod;

    /** 状态: 0-待分摊 1-已分摊 2-已冲销 */
    private Integer status;
}
