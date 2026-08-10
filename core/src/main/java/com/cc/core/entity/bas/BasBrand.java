package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_brands")
public class BasBrand extends BaseEntity {

    /** 品牌名称 */
    private String name;

    /** Logo 地址 */
    private String logoUrl;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
