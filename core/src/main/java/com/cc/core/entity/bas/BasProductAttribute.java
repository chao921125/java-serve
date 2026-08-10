package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品属性值关联
 * 记录每个商品的具体属性值
 * 支持预设值关联（attribute_value_id）和手动输入值（manual_value）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_product_attribute")
public class BasProductAttribute extends BaseEntity {

    /** 商品ID */
    private Long productId;

    /** 属性ID */
    private Long attributeId;

    /** 预设属性值ID，为null时使用manual_value */
    private Long attributeValueId;

    /** 手动输入值，value_type为INPUT/NUMBER时使用 */
    private String manualValue;
}
