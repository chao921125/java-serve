package com.cc.core.entity.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 合同明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_contract_item")
public class BizContractItem extends BaseEntity {

    /** 合同ID */
    private Long contractId;

    /** 商品ID */
    private Long productId;

    /** 数量 */
    private BigDecimal quantity;

    /** 单价 */
    private BigDecimal unitPrice;

    /** 金额 */
    private BigDecimal totalAmount;

    /** 已交付数量 */
    private BigDecimal deliveredQuantity;
}
