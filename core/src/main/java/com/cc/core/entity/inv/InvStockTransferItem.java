package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 调拨单明细
 */
@Data
@TableName("inv_stock_transfer_items")
public class InvStockTransferItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 调拨单 ID */
    private Long transferId;

    /** 商品 ID */
    private Long productId;

    /** 批次号 */
    private String batchNo;

    /** 数量 */
    private BigDecimal quantity;

    /** 成本单价 */
    private BigDecimal costPrice;

    /** 金额 */
    private BigDecimal amount;
}
