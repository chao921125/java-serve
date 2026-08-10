package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.sal.SalesReceiptQueryDTO;
import com.cc.core.entity.sal.SalSalesReceipt;
import com.cc.core.service.sal.SalSalesReceiptService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 销售收款单控制器
 */
@RestController
@RequestMapping("/api/v1/sales-receipts")
@RequiredArgsConstructor
public class SalSalesReceiptController {

    private final SalSalesReceiptService salesReceiptService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<SalSalesReceipt>> list(SalesReceiptQueryDTO query) {
        return R.ok(salesReceiptService.page(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<SalSalesReceipt> detail(@PathVariable Long id) {
        SalSalesReceipt receipt = salesReceiptService.getById(id);
        if (receipt == null) {
            return R.fail("销售收款单不存在");
        }
        return R.ok(receipt);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody SalSalesReceipt receipt) {
        salesReceiptService.save(receipt);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        SalSalesReceipt receipt = salesReceiptService.getById(id);
        if (receipt == null) {
            return R.fail("销售收款单不存在");
        }
        if (receipt.getStatus() != 0) {
            return R.fail("只有草稿状态的收款单才能删除");
        }
        return salesReceiptService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核（核心：核销应收账款、更新账户余额、写账户流水）
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        salesReceiptService.approve(id);
        return R.ok();
    }
}
