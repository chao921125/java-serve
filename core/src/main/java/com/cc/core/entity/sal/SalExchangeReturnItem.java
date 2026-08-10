package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 换货退回明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_exchange_return_item")
public class SalExchangeReturnItem extends BaseEntity {

    /** 换货单ID */
    private Long exchangeId;

    /** 退回商品ID */
    private Long productId;

    /** 数量 */
    private BigDecimal quantity;

    /** 单价 */
    private BigDecimal unitPrice;

    /** 金额 */
    private BigDecimal totalAmount;

    /** 批次号 */
    private String batchNo;

    /** 退货原因: QUALITY/SIZE/WRONG/OTHER */
    private String reason;
}
