package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 仓库
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_warehouses")
public class BasWarehouse extends BaseEntity {

    /** 仓库编码 */
    private String code;

    /** 仓库名称 */
    private String name;

    /** 仓库类型 */
    private Integer type;

    /** 管理员 ID */
    private Long managerId;

    /** 地址 */
    private String address;

    /** 是否默认仓库 0-否 1-是 */
    private Integer isDefault;

    /** 状态 0-正常 1-停用 */
    private Integer status;

    /** 仓库类型: MAIN(总仓), STORE(门店仓), REGIONAL(区域仓) */
    private String warehouseType;

    /** 上级仓库 ID */
    private Long parentWarehouseId;
}
