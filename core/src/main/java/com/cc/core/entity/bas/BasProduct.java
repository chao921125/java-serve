package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_products")
public class BasProduct extends BaseEntity {

    /** 商品编码 */
    private String productCode;

    /** 条码 */
    private String barcode;

    /** 商品名称 */
    private String name;

    /** 规格 */
    private String spec;

    /** 分类 ID */
    private Long categoryId;

    /** 单位 ID */
    private Long unitId;

    /** 品牌 ID */
    private Long brandId;

    /** 基础价格 */
    private BigDecimal basePrice;

    /** 销售价格 */
    private BigDecimal salePrice;

    /** 最低销售价格 */
    private BigDecimal minSalePrice;

    /** 默认供应商 ID */
    private Long defaultSupplierId;

    /** 是否批次管理 0-否 1-是 */
    private Integer isBatchManage;

    /** 是否效期管理 0-否 1-是 */
    private Integer isExpiryManage;

    /** 是否序列号管理 0-否 1-是 */
    private Integer isSerialManage;

    /** 允许负库存 0-否 1-是 */
    private Integer allowNegative;

    /** 图片地址 */
    private String imageUrl;

    /** 状态 0-正常 1-停用 */
    private Integer status;

    /** 成本核算方法: WEIGHTED_AVG(移动加权平均), FIFO(先进先出), SPECIFIC(个别计价) */
    private String costingMethod;
}
