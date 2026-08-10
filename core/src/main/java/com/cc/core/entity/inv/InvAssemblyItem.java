package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 组装拆卸单明细
 */
@Data
@TableName("inv_assembly_items")
public class InvAssemblyItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 组装拆卸单 ID */
    private Long assemblyId;

    /** 组件商品 ID */
    private Long componentProductId;

    /** 数量 */
    private BigDecimal quantity;

    /** 成本单价 */
    private BigDecimal costPrice;
}
