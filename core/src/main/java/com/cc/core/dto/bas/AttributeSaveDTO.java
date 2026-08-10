package com.cc.core.dto.bas;

import lombok.Data;

/**
 * 属性定义新增/修改
 */
@Data
public class AttributeSaveDTO {

    /** 属性名称，如"口径"、"壁厚"、"材质" */
    private String name;

    /** 属性编码，唯一 */
    private String code;

    /** 所属模板ID */
    private Long templateId;

    /** 属性类型: SELECT(下拉选择), INPUT(文本), NUMBER(数字), DATE(日期), BOOL(布尔) */
    private String valueType;

    /** 是否必填 0-否 1-是 */
    private Integer isRequired;

    /** 是否用于SKU生成 0-否 1-是 */
    private Integer isSku;

    /** 排序号 */
    private Integer sortOrder;

    /** 备注 */
    private String remark;
}
