package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 属性定义
 * 定义模板下的具体属性，如"口径"、"壁厚"、"材质"、"压力等级"等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_attribute")
public class BasAttribute extends BaseEntity {

    /** 关联属性模板ID */
    private Long templateId;

    /** 属性名称 */
    private String name;

    /** 属性编码 */
    private String code;

    /** 值类型: SELECT(单选) / MULTI_SELECT(多选) / INPUT(文本) / NUMBER(数字) */
    private String valueType;

    /** 是否必填 0-否 1-是 */
    private Integer isRequired;

    /** 是否可搜索 0-否 1-是 */
    private Integer isSearchable;

    /** 是否生成SKU 0-否 1-是 */
    private Integer isSkuGenerate;

    /** 排序号 */
    private Integer sortOrder;
}
