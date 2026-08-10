package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 库存预留
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inv_stock_reservation")
public class InvStockReservation extends BaseEntity {

    /** 预留单号 */
    private String reservationNo;

    /** 商品ID */
    private Long productId;

    /** 仓库ID */
    private Long warehouseId;

    /** 批次号 */
    private String batchNo;

    /** 预留数量 */
    private BigDecimal quantity;

    /** 已释放数量 */
    private BigDecimal releasedQuantity;

    /** 来源单据类型: SALES_ORDER/STOCK_TRANSFER/ASSEMBLY */
    private String sourceType;

    /** 来源单据ID */
    private Long sourceId;

    /** 来源单据明细ID */
    private Long sourceItemId;

    /** 状态: 0-已预留 1-已释放 2-已出库 3-已取消 */
    private Integer status;

    /** 预留人 */
    private String reservedBy;

    /** 预留时间 */
    private String reservedTime;

    /** 自动释放时间 */
    private String expireTime;
}
