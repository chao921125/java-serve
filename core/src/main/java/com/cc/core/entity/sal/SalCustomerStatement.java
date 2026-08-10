package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 客户对账单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sal_customer_statement")
public class SalCustomerStatement extends BaseEntity {

    /** 对账单号 */
    private String statementNo;

    /** 客户ID */
    private Long customerId;

    /** 对账开始日期 */
    private LocalDate startDate;

    /** 对账结束日期 */
    private LocalDate endDate;

    /** 期初应收 */
    private BigDecimal openingReceivable;

    /** 本期销售金额 */
    private BigDecimal salesAmount;

    /** 本期退货金额 */
    private BigDecimal returnAmount;

    /** 本期收款金额 */
    private BigDecimal receiptAmount;

    /** 期末应收 */
    private BigDecimal closingReceivable;

    /** 状态: 0-草稿 1-待确认 2-已确认 3-有争议 */
    private Integer status;

    /** 确认人 */
    private String confirmedBy;

    /** 确认时间 */
    private String confirmedTime;
}
