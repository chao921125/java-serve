package com.cc.core.entity.inv;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 保质期预警
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inv_expiry_alert")
public class InvExpiryAlert extends BaseEntity {

    /** 商品ID */
    private Long productId;

    /** 仓库ID */
    private Long warehouseId;

    /** 批次号 */
    private String batchNo;

    /** 到期日期 */
    private LocalDate expiryDate;

    /** 批次数量 */
    private BigDecimal quantity;

    /** 剩余天数 */
    private Integer remainingDays;

    /** 预警级别: EXPIRED/URGENT/WARNING/NOTICE */
    private String alertLevel;

    /** 预警触发时间 */
    private String alertTime;

    /** 已处理 0-否 1-是 */
    private Integer handled;

    /** 处理时间 */
    private String handledTime;

    /** 处理方式: DISCOUNT/RETURN/DISPOSAL/OTHER */
    private String handleMethod;
}
