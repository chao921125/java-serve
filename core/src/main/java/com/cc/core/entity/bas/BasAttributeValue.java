package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 属性预设值
 * 为 SELECT/MULTI_SELECT 类型的属性定义可选值列表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_attribute_value")
public class BasAttributeValue extends BaseEntity {

    /** 关联属性ID */
    private Long attributeId;

    /** 属性值，如"DN50"、"304不锈钢"、"PN16" */
    private String value;

    /** 排序号 */
    private Integer sortOrder;
}
