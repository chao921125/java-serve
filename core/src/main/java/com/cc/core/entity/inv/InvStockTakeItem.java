package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 盘点单明细
 */
@Data
@TableName("inv_stock_take_items")
public class InvStockTakeItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 盘点单 ID */
    private Long takeId;

    /** 商品 ID */
    private Long productId;

    /** 批次号 */
    private String batchNo;

    /** 账面数量 */
    private BigDecimal bookQuantity;

    /** 实际数量 */
    private BigDecimal actualQuantity;

    /** 成本单价 */
    private BigDecimal costPrice;

    /** 备注 */
    private String remark;
}
