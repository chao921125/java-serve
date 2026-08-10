package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 销售收款单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_sales_receipts")
public class SalSalesReceipt extends BaseEntity {

    /** 收款单号 */
    private String receiptNo;

    /** 客户 ID */
    private Long customerId;

    /** 账户 ID */
    private Long accountId;

    /** 收款日期 */
    private LocalDate receiptDate;

    /** 金额 */
    private BigDecimal amount;

    /** 付款方式 0-现金 1-银行 2-支付宝 3-微信 */
    private Integer payType;

    /** 状态 0-草稿 1-待审核 2-已审核 3-已完成 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;
}
