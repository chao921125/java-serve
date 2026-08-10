package com.cc.app.controller.inv;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.entity.inv.InvInventoryTransaction;
import com.cc.core.service.inv.InvInventoryTransactionService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 库存流水控制器
 */
@RestController
@RequestMapping("/api/v1/inventory/transactions")
@RequiredArgsConstructor
public class InvInventoryTransactionController {

    private final InvInventoryTransactionService transactionService;

    /**
     * 分页查询库存流水
     */
    @GetMapping
    public R<IPage<InvInventoryTransaction>> list(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return R.ok(transactionService.page(productId, warehouseId, page, pageSize));
    }
}
