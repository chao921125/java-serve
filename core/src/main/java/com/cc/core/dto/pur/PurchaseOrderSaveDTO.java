package com.cc.core.dto.pur;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 采购订单新增/修改
 */
@Data
public class PurchaseOrderSaveDTO {

    /** 供应商 ID */
    @NotNull(message = "供应商不能为空")
    private Long supplierId;

    /** 仓库 ID */
    @NotNull(message = "仓库不能为空")
    private Long warehouseId;

    /** 订单日期 */
    @NotNull(message = "订单日期不能为空")
    private LocalDate orderDate;

    /** 预计到货日期 */
    private LocalDate expectedDate;

    /** 明细列表 */
    @Valid
    @NotEmpty(message = "订单明细不能为空")
    private List<PurchaseOrderItemDTO> items;

    /** 备注 */
    private String remark;
}
