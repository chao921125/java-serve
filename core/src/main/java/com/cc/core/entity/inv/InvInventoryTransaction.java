package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 库存流水
 */
@Data
@TableName("inv_inventory_transactions")
public class InvInventoryTransaction {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 仓库 ID */
    private Long warehouseId;

    /** 商品 ID */
    private Long productId;

    /** 批次号 */
    private String batchNo;

    /** 交易类型 1-采购入库 2-采购退货出库 3-销售出库 4-销售退货入库 ... */
    private Integer transactionType;

    /** 数量 */
    private BigDecimal quantity;

    /** 变更前数量 */
    private BigDecimal beforeQuantity;

    /** 变更后数量 */
    private BigDecimal afterQuantity;

    /** 成本单价 */
    private BigDecimal costPrice;

    /** 来源类型 */
    private String sourceType;

    /** 来源 ID */
    private Long sourceId;

    /** 来源单号 */
    private String sourceNo;

    /** 交易时间 */
    private LocalDateTime transactionTime;

    /** 操作人 ID */
    private Long operatorId;

    /** 备注 */
    private String remark;
}
