// AI生成
package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 销售出库单主表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_sales_deliveries")
public class SalSalesDelivery extends BaseEntity {

    /** 出库单号 */
    private String deliveryNo;

    /** 销售订单 ID */
    private Long orderId;

    /** 客户 ID */
    private Long customerId;

    /** 仓库 ID */
    private Long warehouseId;

    /** 出库日期 */
    private LocalDate deliveryDate;

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

    /** 明细列表（非表字段） */
    @TableField(exist = false)
    private List<SalSalesDeliveryItem> items;
}
