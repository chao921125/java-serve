package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售换货单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_exchange_order")
public class SalExchangeOrder extends BaseEntity {

    /** 换货单号 */
    private String exchangeNo;

    /** 换货日期 */
    private LocalDate exchangeDate;

    /** 客户ID */
    private Long customerId;

    /** 关联销售订单ID */
    private Long salesOrderId;

    /** 关联销售出库单ID */
    private Long deliveryId;

    /** 状态: 0-草稿 1-待审核 2-已审核 3-已完成 */
    private Integer status;

    /** 换货原因 */
    private String exchangeReason;

    /** 退回商品总金额 */
    private BigDecimal returnTotal;

    /** 换出商品总金额 */
    private BigDecimal exchangeTotal;

    /** 差额 */
    private BigDecimal differenceAmount;

    /** 仓库ID */
    private Long warehouseId;

    /** 审核人ID */
    private Long approverId;

    /** 审核时间 */
    private String approveTime;
}
