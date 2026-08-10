package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 采购费用分摊明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_expense_allocation_detail")
public class PurExpenseAllocationDetail extends BaseEntity {

    /** 分摊单ID */
    private Long allocationId;

    /** 入库单明细ID */
    private Long receiptItemId;

    /** 商品ID */
    private Long productId;

    /** 数量 */
    private BigDecimal quantity;

    /** 分摊金额 */
    private BigDecimal amount;
}
