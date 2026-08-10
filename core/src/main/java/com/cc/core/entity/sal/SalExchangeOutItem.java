package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 换货发出明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_exchange_out_item")
public class SalExchangeOutItem extends BaseEntity {

    /** 换货单ID */
    private Long exchangeId;

    /** 换出商品ID */
    private Long productId;

    /** 数量 */
    private BigDecimal quantity;

    /** 单价 */
    private BigDecimal unitPrice;

    /** 金额 */
    private BigDecimal totalAmount;

    /** 批次号 */
    private String batchNo;
}
