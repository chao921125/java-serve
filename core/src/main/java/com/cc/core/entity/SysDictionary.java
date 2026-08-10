package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统字典
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dictionary")
public class SysDictionary extends BaseEntity {

    /** 字典名称 */
    private String name;

    /** 字典值（JSON 格式） */
    private String value;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
