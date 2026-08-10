package com.cc.core.entity.fin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 收款单明细
 */
@Data
@TableName("fin_receipt_items")
public class FinReceiptItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 收款单 ID */
    private Long receiptId;

    /** 应收账款 ID */
    private Long receivableId;

    /** 核销金额 */
    private BigDecimal amount;
}
