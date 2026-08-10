package com.cc.core.dto.sal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 销售订单新增/修改
 */
@Data
public class SalesOrderSaveDTO {

    /** 客户 ID */
    @NotNull(message = "客户不能为空")
    private Long customerId;

    /** 仓库 ID */
    @NotNull(message = "仓库不能为空")
    private Long warehouseId;

    /** 订单日期 */
    @NotNull(message = "订单日期不能为空")
    private LocalDate orderDate;

    /** 预计交货日期 */
    private LocalDate expectedDate;

    /** 明细列表 */
    @Valid
    @NotEmpty(message = "订单明细不能为空")
    private List<SalesOrderItemDTO> items;

    /** 备注 */
    private String remark;
}
