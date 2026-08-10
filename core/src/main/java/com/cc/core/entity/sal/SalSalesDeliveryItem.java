package com.cc.core.entity.sal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 销售出库单明细表（不继承 BaseEntity）
 */
@Data
@TableName("sal_sales_delivery_items")
public class SalSalesDeliveryItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 出库单 ID */
    private Long deliveryId;

    /** 销售订单明细 ID */
    private Long orderItemId;

    /** 商品 ID */
    private Long productId;

    /** 批次号 */
    private String batchNo;

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
