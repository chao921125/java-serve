package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 库位
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_locations")
public class BasLocation extends BaseEntity {

    /** 仓库 ID */
    private Long warehouseId;

    /** 库位编码 */
    private String code;

    /** 库位名称 */
    private String name;

    /** 区域 */
    private String area;

    /** 状态 0-正常 1-停用 */
    private Integer status;
}
