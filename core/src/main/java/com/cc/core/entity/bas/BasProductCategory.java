// AI生成
package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品分类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_product_categories")
public class BasProductCategory extends BaseEntity {

    /** 父级 ID */
    private Long parentId;

    /** 分类名称 */
    private String name;

    /** 分类编码 */
    private String code;

    /** 层级 */
    private Integer level;

    /** 排序 */
    private Integer sort;

    /** 状态 0-正常 1-停用 */
    private Integer status;

    /** 子节点（非表字段） */
    @TableField(exist = false)
    private List<BasProductCategory> children;
}
