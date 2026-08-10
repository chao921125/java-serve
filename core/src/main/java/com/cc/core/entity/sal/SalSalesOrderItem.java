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
 * 销售订单明细表（不继承 BaseEntity）
 */
@Data
@TableName("sal_sales_order_items")
public class SalSalesOrderItem {

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

    /** 已发货数量 */
    private BigDecimal deliveredQuantity;

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
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
