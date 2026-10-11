// AI生成
package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.sal.SalesReceiptQueryDTO;
import com.cc.core.entity.sal.SalSalesReceipt;
import com.cc.core.service.sal.SalSalesReceiptService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

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
    public R<Long> create(@RequestBody SalSalesReceipt receipt) {
        receipt.setReceiptNo("RC" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ThreadLocalRandom.current().nextInt(1000, 9999));
        if (receipt.getStatus() == null) receipt.setStatus(0);
        salesReceiptService.save(receipt);
        return R.ok(receipt.getId());
    }

    /**
     * 提交审核（草稿→待审核）
     */
    @PostMapping("/{id}/submit")
    public R<Void> submit(@PathVariable Long id) {
        SalSalesReceipt receipt = salesReceiptService.getById(id);
        if (receipt == null) return R.fail("收款单不存在");
        if (receipt.getStatus() != 0) return R.fail("只有草稿状态才能提交");
        receipt.setStatus(1);
        salesReceiptService.updateById(receipt);
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
