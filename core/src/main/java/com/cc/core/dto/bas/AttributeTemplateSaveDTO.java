package com.cc.core.dto.bas;

import lombok.Data;

/**
 * 属性模板新增/修改
 */
@Data
public class AttributeTemplateSaveDTO {

    /** 模板名称 */
    private String name;

    /** 模板编码 */
    private String code;

    /** 关联商品分类ID，为空表示全局模板 */
    private Long categoryId;

    /** 排序号 */
    private Integer sortOrder;

    /** 备注 */
    private String remark;
}
