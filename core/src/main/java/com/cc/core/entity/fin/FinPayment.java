package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 付款单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fin_payments")
public class FinPayment extends BaseEntity {

    /** 付款单号 */
    private String paymentNo;

    /** 供应商 ID */
    private Long supplierId;

    /** 账户 ID */
    private Long accountId;

    /** 付款日期 */
    private LocalDate paymentDate;

    /** 金额 */
    private BigDecimal amount;

    /** 付款方式 0-现金 1-银行 2-支付宝 3-微信 */
    private Integer payType;

    /** 状态 0-草稿 1-待审 2-已审 */
    private Integer status;

    /** 审核人 ID */
    private Long approverId;

    /** 审核时间 */
    private LocalDateTime approveTime;
}
