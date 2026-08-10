package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 请购单明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_purchase_requisition_item")
public class PurPurchaseRequisitionItem extends BaseEntity {

    /** 请购单ID */
    private Long requisitionId;

    /** 商品ID */
    private Long productId;

    /** 数量 */
    private BigDecimal quantity;

    /** 预计单价 */
    private BigDecimal estimatedPrice;

    /** 预计金额 */
    private BigDecimal estimatedAmount;
}
