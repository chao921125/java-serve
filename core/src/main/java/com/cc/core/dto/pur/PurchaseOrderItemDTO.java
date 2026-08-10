package com.cc.core.dto.pur;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 采购订单明细 DTO
 */
@Data
public class PurchaseOrderItemDTO {

    /** 商品 ID */
    @NotNull(message = "商品不能为空")
    private Long productId;

    /** 单位 ID */
    @NotNull(message = "单位不能为空")
    private Long unitId;

    /** 数量 */
    @NotNull(message = "数量不能为空")
    private BigDecimal quantity;

    /** 含税单价 */
    @NotNull(message = "单价不能为空")
    private BigDecimal price;

    /** 税率 */
    private BigDecimal taxRate;

    /** 备注 */
    private String remark;

    /** 排序 */
    private Integer sort;
}
