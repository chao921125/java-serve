// AI生成
package com.cc.core.dto.bas;

import lombok.Data;

/**
 * 商品查询条件
 */
@Data
public class ProductQueryDTO {

    /** 页码 */
    private Integer page;

    /** 每页大小 */
    private Integer pageSize;

    /** 商品编码 */
    private String productCode;

    /** 商品名称 */
    private String name;

    /** 分类 ID */
    private Long categoryId;

    /** 材质A */
    private String material;

    /** 标准 */
    private String standard;

    /** 类型 */
    private String productType;

    /** 仓库 ID */
    private Long warehouseId;

    /** 状态 */
    private Integer status;
}
