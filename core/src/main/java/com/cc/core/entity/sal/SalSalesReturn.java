package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 销售退货单主表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_sales_returns")
public class SalSalesReturn extends BaseEntity {

    /** 退货单号 */
    private String returnNo;

    /** 出库单 ID */
    private Long deliveryId;

    /** 客户 ID */
    private Long customerId;

    /** 仓库 ID */
    private Long warehouseId;

    /** 退货日期 */
    private LocalDate returnDate;

    /** 总数量 */
    private BigDecimal totalQuantity;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 状态 0-草稿 1-待审核 2-已审核 3-已完成 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;
}
