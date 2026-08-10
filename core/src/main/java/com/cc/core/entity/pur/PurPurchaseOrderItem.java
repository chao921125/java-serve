package com.cc.core.entity.pur;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 采购订单明细表（不继承 BaseEntity）
 */
@Data
@TableName("pur_purchase_order_items")
public class PurPurchaseOrderItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 订单 ID */
    private Long orderId;

    /** 商品 ID */
    private Long productId;

    /** 单位 ID */
    private Long unitId;

    /** 数量 */
    private BigDecimal quantity;

    /** 已收数量 */
    private BigDecimal receivedQuantity;

    /** 含税单价 */
    private BigDecimal price;

    /** 税率 */
    private BigDecimal taxRate;

    /** 金额（不含税） */
    private BigDecimal amount;

    /** 税额 */
    private BigDecimal taxAmount;

    /** 总金额（含税） */
    private BigDecimal totalAmount;

    /** 备注 */
    private String remark;

    /** 排序 */
    private Integer sort;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private java.time.LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private java.time.LocalDateTime updateTime;
}
