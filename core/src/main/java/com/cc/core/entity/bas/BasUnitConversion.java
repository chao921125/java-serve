package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 单位换算
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_unit_conversions")
public class BasUnitConversion extends BaseEntity {

    /** 商品 ID */
    private Long productId;

    /** 源单位 ID */
    private Long fromUnitId;

    /** 目标单位 ID */
    private Long toUnitId;

    /** 换算比率 */
    private BigDecimal ratio;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
