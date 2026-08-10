package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 库存库龄快照
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inv_stock_aging")
public class InvStockAging extends BaseEntity {

    /** 快照日期 */
    private LocalDate snapshotDate;

    /** 商品ID */
    private Long productId;

    /** 仓库ID */
    private Long warehouseId;

    /** 批次号 */
    private String batchNo;

    /** 库存数量 */
    private BigDecimal quantity;

    /** 库存成本金额 */
    private BigDecimal costAmount;

    /** 最后入库日期 */
    private LocalDate lastInboundDate;

    /** 库龄天数 */
    private Integer agingDays;

    /** 库龄区间: 0-30/31-60/61-90/91-180/181-365/365+ */
    private String agingBucket;

    /** 周转率 */
    private BigDecimal turnoverRate;

    /** 是否呆滞品 0-否 1-是 */
    private Integer isSlowMoving;
}
