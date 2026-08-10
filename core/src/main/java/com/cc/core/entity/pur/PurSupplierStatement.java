package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 供应商对账单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pur_supplier_statement")
public class PurSupplierStatement extends BaseEntity {

    /** 对账单号 */
    private String statementNo;

    /** 供应商ID */
    private Long supplierId;

    /** 对账开始日期 */
    private LocalDate startDate;

    /** 对账结束日期 */
    private LocalDate endDate;

    /** 期初应付 */
    private BigDecimal openingPayable;

    /** 本期采购金额 */
    private BigDecimal purchaseAmount;

    /** 本期退货金额 */
    private BigDecimal returnAmount;

    /** 本期付款金额 */
    private BigDecimal paymentAmount;

    /** 期末应付 */
    private BigDecimal closingPayable;

    /** 状态: 0-草稿 1-待确认 2-已确认 3-有争议 */
    private Integer status;

    /** 确认人 */
    private String confirmedBy;

    /** 确认时间 */
    private String confirmedTime;
}
