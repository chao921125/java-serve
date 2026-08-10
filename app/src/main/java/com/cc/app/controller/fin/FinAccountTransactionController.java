package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.entity.fin.FinAccountTransaction;
import com.cc.core.service.fin.FinAccountTransactionService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 资金账户流水控制器
 */
@RestController
@RequestMapping("/api/v1/account-transactions")
@RequiredArgsConstructor
public class FinAccountTransactionController {

    private final FinAccountTransactionService transactionService;

    /**
     * 分页查询账户流水
     */
    @GetMapping
    public R<IPage<FinAccountTransaction>> list(
            @RequestParam(required = false) Long accountId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return R.ok(transactionService.page(accountId, page, pageSize));
    }
}
