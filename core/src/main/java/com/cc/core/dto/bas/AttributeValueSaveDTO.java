package com.cc.core.dto.bas;

import lombok.Data;

/**
 * 属性预设值新增/修改
 */
@Data
public class AttributeValueSaveDTO {

    /** 所属属性ID */
    private Long attributeId;

    /** 预设值 */
    private String value;

    /** 排序号 */
    private Integer sortOrder;
}
