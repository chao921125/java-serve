package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 打印模板
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_print_template")
public class SysPrintTemplate extends BaseEntity {

    /** 模板名称 */
    private String name;

    /** 模板编码 */
    private String code;

    /** 业务类型 */
    private String businessType;

    /** 模板内容(HTML/FreeMarker) */
    private String templateContent;

    /** 纸张大小: A4/A5/80mm_continuous */
    private String pageSize;

    /** 是否默认模板 */
    private Integer isDefault;

    /** 启用状态 */
    private Integer isEnabled;
}
