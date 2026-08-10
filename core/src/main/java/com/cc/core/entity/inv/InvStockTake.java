package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 盘点单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inv_stock_takes")
public class InvStockTake extends BaseEntity {

    /** 盘点单号 */
    private String takeNo;

    /** 仓库 ID */
    private Long warehouseId;

    /** 盘点类型 0-全盘 1-抽盘 */
    private Integer takeType;

    /** 盘点日期 */
    private LocalDate takeDate;

    /** 差异数量合计 */
    private BigDecimal totalDiffQuantity;

    /** 差异金额合计 */
    private BigDecimal totalDiffAmount;

    /** 状态 0-草稿 1-盘点中 2-待审核 3-已完成 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;
}
