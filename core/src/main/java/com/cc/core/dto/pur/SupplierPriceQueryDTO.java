package com.cc.core.dto.pur;

import lombok.Data;

/**
 * 供应商价格查询条件
 */
@Data
public class SupplierPriceQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 供应商 ID */
    private Long supplierId;

    /** 商品 ID */
    private Long productId;

    /** 状态 */
    private Integer status;
}
