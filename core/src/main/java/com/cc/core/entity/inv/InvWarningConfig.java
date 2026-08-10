package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 库存预警配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inv_warning_configs")
public class InvWarningConfig extends BaseEntity {

    /** 商品 ID */
    private Long productId;

    /** 仓库 ID */
    private Long warehouseId;

    /** 最低库存量 */
    private BigDecimal minQuantity;

    /** 最高库存量 */
    private BigDecimal maxQuantity;

    /** 状态 0-启用 1-停用 */
    private Integer status;

    /** 保质期预警提前天数 */
    private Integer expiryWarningDays;

    /** 是否启用保质期预警 0-否 1-是 */
    private Integer expiryWarningEnabled;
}
