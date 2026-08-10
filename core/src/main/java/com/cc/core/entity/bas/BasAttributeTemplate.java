package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 属性模板
 * 用于定义商品辅助属性的模板，如"管道属性"、"阀门属性"等
 * 可关联商品分类，也可作为全局模板
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_attribute_template")
public class BasAttributeTemplate extends BaseEntity {

    /** 模板名称 */
    private String name;

    /** 模板编码，唯一 */
    private String code;

    /** 关联商品分类ID，为空表示全局模板 */
    private Long categoryId;

    /** 启用状态 0-停用 1-启用 */
    private Integer isEnabled;

    /** 排序号 */
    private Integer sortOrder;
}
