package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 采购入库单明细表（不继承 BaseEntity）
 */
@Data
@TableName("pur_purchase_receipt_items")
public class PurPurchaseReceiptItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 入库单 ID */
    private Long receiptId;

    /** 采购订单明细 ID */
    private Long orderItemId;

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

    /** 单价 */
    private BigDecimal price;

    /** 金额 */
    private BigDecimal amount;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
