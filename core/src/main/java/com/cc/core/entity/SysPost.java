package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统岗位
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_post")
public class SysPost extends BaseEntity {

    /** 岗位编码 */
    private String code;

    /** 岗位名称 */
    private String name;

    /** 排序 */
    private Integer sort;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
