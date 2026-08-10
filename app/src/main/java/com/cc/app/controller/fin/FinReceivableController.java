package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.fin.ReceivableQueryDTO;
import com.cc.core.entity.fin.FinReceivable;
import com.cc.core.service.fin.FinReceivableService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * 应收账款控制器
 */
@RestController
@RequestMapping("/api/v1/receivables")
@RequiredArgsConstructor
public class FinReceivableController {

    private final FinReceivableService receivableService;

    /**
     * 分页查询（含账龄）
     */
    @GetMapping
    public R<IPage<Map<String, Object>>> list(ReceivableQueryDTO query) {
        IPage<FinReceivable> pageResult = receivableService.page(query);
        IPage<Map<String, Object>> result = pageResult.convert(receivable -> {
            Map<String, Object> map = new HashMap<>();
            map.put("receivable", receivable);
            // 计算账龄（天）
            if (receivable.getDueDate() != null) {
                long ageDays = ChronoUnit.DAYS.between(receivable.getDueDate(), LocalDate.now());
                map.put("ageDays", Math.max(0, ageDays));
            } else {
                map.put("ageDays", 0);
            }
            // 未收金额
            BigDecimal balance = receivable.getBalance() != null ? receivable.getBalance() : BigDecimal.ZERO;
            map.put("unreceivedAmount", balance);
            return map;
        });
        return R.ok(result);
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<FinReceivable> detail(@PathVariable Long id) {
        FinReceivable receivable = receivableService.getById(id);
        if (receivable == null) {
            return R.fail("应收账款不存在");
        }
        return R.ok(receivable);
    }
}
