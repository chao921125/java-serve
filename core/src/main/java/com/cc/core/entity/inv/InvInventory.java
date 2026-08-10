package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 库存
 */
@Data
@TableName("inv_inventory")
public class InvInventory {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 仓库 ID */
    private Long warehouseId;

    /** 商品 ID */
    private Long productId;

    /** 批次号 */
    private String batchNo;

    /** 生产日期 */
    private LocalDate productionDate;

    /** 过期日期 */
    private LocalDate expiryDate;

    /** 数量 */
    private BigDecimal quantity;

    /** 锁定数量 */
    private BigDecimal lockedQuantity;

    /** 可用数量（计算列） */
    private BigDecimal availableQuantity;

    /** 成本单价 */
    private BigDecimal costPrice;

    /** 总成本 */
    private BigDecimal totalCost;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
