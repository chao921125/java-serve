package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.fin.PaymentQueryDTO;
import com.cc.core.entity.fin.FinPayment;
import com.cc.core.entity.fin.FinPaymentItem;
import com.cc.core.service.fin.FinPaymentService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 付款单控制器
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class FinPaymentController {

    private final FinPaymentService paymentService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<FinPayment>> list(PaymentQueryDTO query) {
        return R.ok(paymentService.page(query));
    }

    /**
     * 详情（含明细）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        FinPayment payment = paymentService.getById(id);
        if (payment == null) {
            return R.fail("付款单不存在");
        }
        List<FinPaymentItem> items = paymentService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("payment", payment);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody FinPayment payment) {
        payment.setStatus(0);
        paymentService.save(payment);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody FinPayment payment) {
        payment.setId(id);
        paymentService.updateById(payment);
        return R.ok();
    }

    /**
     * 删除（仅草稿状态）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        FinPayment payment = paymentService.getById(id);
        if (payment == null) {
            return R.fail("付款单不存在");
        }
        if (payment.getStatus() != 0) {
            return R.fail("只有草稿状态的付款单才能删除");
        }
        return paymentService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        paymentService.approve(id);
        return R.ok();
    }
}
