package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.fin.PayableQueryDTO;
import com.cc.core.entity.fin.FinPayable;
import com.cc.core.service.fin.FinPayableService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * 应付账款控制器
 */
@RestController
@RequestMapping("/api/v1/payables")
@RequiredArgsConstructor
public class FinPayableController {

    private final FinPayableService payableService;

    /**
     * 分页查询（含账龄）
     */
    @GetMapping
    public R<IPage<Map<String, Object>>> list(PayableQueryDTO query) {
        IPage<FinPayable> pageResult = payableService.page(query);
        IPage<Map<String, Object>> result = pageResult.convert(payable -> {
            Map<String, Object> map = new HashMap<>();
            map.put("payable", payable);
            // 计算账龄（天）
            if (payable.getDueDate() != null) {
                long ageDays = ChronoUnit.DAYS.between(payable.getDueDate(), LocalDate.now());
                map.put("ageDays", Math.max(0, ageDays));
            } else {
                map.put("ageDays", 0);
            }
            // 未付金额
            BigDecimal balance = payable.getBalance() != null ? payable.getBalance() : BigDecimal.ZERO;
            map.put("unpaidAmount", balance);
            return map;
        });
        return R.ok(result);
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<FinPayable> detail(@PathVariable Long id) {
        FinPayable payable = payableService.getById(id);
        if (payable == null) {
            return R.fail("应付账款不存在");
        }
        return R.ok(payable);
    }
}
