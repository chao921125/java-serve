package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 供应商价格表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_supplier_prices")
public class PurSupplierPrice extends BaseEntity {

    /** 供应商 ID */
    private Long supplierId;

    /** 商品 ID */
    private Long productId;

    /** 价格 */
    private BigDecimal price;

    /** 生效日期 */
    private LocalDate effectiveDate;

    /** 失效日期 */
    private LocalDate expiryDate;

    /** 状态 0-停用 1-正常 */
    private Integer status;
}
