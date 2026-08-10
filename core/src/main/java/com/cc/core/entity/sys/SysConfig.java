package com.cc.core.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
public class SysConfig extends BaseEntity {

    /** 配置键 */
    private String configKey;

    /** 配置值 */
    private String configValue;

    /** 类型: STRING/NUMBER/BOOLEAN/JSON */
    private String configType;

    /** 分组: SYSTEM/BUSINESS/EMAIL/SMS/WECHAT */
    private String configGroup;

    /** 配置说明 */
    private String description;

    /** 是否系统内置 */
    private Integer isSystem;

    /** 排序号 */
    private Integer sortOrder;
}
