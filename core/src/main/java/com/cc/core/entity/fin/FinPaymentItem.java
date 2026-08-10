package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 付款单明细
 */
@Data
@TableName("fin_payment_items")
public class FinPaymentItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 付款单 ID */
    private Long paymentId;

    /** 应付账款 ID */
    private Long payableId;

    /** 核销金额 */
    private BigDecimal amount;
}
