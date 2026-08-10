package com.cc.core.dto.inv;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 库存查询条件
 */
@Data
public class InventoryQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 仓库 ID */
    private Long warehouseId;

    /** 商品 ID */
    private Long productId;

    /** 商品名称 */
    private String productName;

    /** 关键词 */
    private String keyword;

    /** 最低数量 */
    private BigDecimal minQuantity;

    /** 最高数量 */
    private BigDecimal maxQuantity;
}
