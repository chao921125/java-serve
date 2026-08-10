package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 报价明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_quote_detail")
public class PurQuoteDetail extends BaseEntity {

    /** 询价单ID */
    private Long inquiryId;

    /** 询价明细ID */
    private Long inquiryItemId;

    /** 询价供应商ID */
    private Long inquirySupplierId;

    /** 供应商ID */
    private Long supplierId;

    /** 商品ID */
    private Long productId;

    /** 报价单价 */
    private BigDecimal unitPrice;

    /** 交货天数 */
    private Integer deliveryDays;

    /** 起订量 */
    private BigDecimal minQuantity;

    /** 付款条件 */
    private String paymentTerms;

    /** 是否选中 0-否 1-是 */
    private Integer isSelected;
}
