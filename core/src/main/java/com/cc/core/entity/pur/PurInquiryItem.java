package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 询价单明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_inquiry_item")
public class PurInquiryItem extends BaseEntity {

    /** 询价单ID */
    private Long inquiryId;

    /** 商品ID */
    private Long productId;

    /** 数量 */
    private BigDecimal quantity;

    /** 规格要求 */
    private String spec;
}
