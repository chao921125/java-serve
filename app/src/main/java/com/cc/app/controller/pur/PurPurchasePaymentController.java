package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.pur.PurchasePaymentQueryDTO;
import com.cc.core.entity.pur.PurPurchasePayment;
import com.cc.core.service.pur.PurPurchasePaymentService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 采购付款单控制器
 */
@RestController
@RequestMapping("/api/v1/purchase-payments")
@RequiredArgsConstructor
public class PurPurchasePaymentController {

    private final PurPurchasePaymentService purchasePaymentService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<PurPurchasePayment>> list(PurchasePaymentQueryDTO query) {
        return R.ok(purchasePaymentService.page(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<PurPurchasePayment> detail(@PathVariable Long id) {
        PurPurchasePayment payment = purchasePaymentService.getById(id);
        if (payment == null) {
            return R.fail("采购付款单不存在");
        }
        return R.ok(payment);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody PurPurchasePayment payment) {
        purchasePaymentService.save(payment);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        PurPurchasePayment payment = purchasePaymentService.getById(id);
        if (payment == null) {
            return R.fail("采购付款单不存在");
        }
        if (payment.getStatus() != 0) {
            return R.fail("只有草稿状态的付款单才能删除");
        }
        return purchasePaymentService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        purchasePaymentService.approve(id);
        return R.ok();
    }
}
