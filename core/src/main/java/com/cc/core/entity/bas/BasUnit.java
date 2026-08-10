package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 计量单位
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_units")
public class BasUnit extends BaseEntity {

    /** 单位名称 */
    private String name;

    /** 单位编码 */
    private String code;

    /** 精度值 */
    @TableField("precision_val")
    private Integer precisionVal;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
