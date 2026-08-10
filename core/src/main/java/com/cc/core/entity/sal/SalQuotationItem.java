package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 报价单明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_quotation_item")
public class SalQuotationItem extends BaseEntity {

    /** 报价单ID */
    private Long quotationId;

    /** 商品ID */
    private Long productId;

    /** 数量 */
    private BigDecimal quantity;

    /** 单价 */
    private BigDecimal unitPrice;

    /** 单品折扣率% */
    private BigDecimal discountRate;

    /** 折扣金额 */
    private BigDecimal discountAmount;

    /** 税率% */
    private BigDecimal taxRate;

    /** 税额 */
    private BigDecimal taxAmount;

    /** 小计金额 */
    private BigDecimal totalAmount;

    /** 成本价 */
    private BigDecimal costPrice;
}
