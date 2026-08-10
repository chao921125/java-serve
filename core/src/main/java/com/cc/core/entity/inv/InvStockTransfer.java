package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 调拨单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inv_stock_transfers")
public class InvStockTransfer extends BaseEntity {

    /** 调拨单号 */
    private String transferNo;

    /** 调出仓库 ID */
    private Long fromWarehouseId;

    /** 调入仓库 ID */
    private Long toWarehouseId;

    /** 调拨日期 */
    private LocalDate transferDate;

    /** 总数量 */
    private BigDecimal totalQuantity;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 状态 0-草稿 1-待审 2-已审 3-已完成 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;
}
