package com.cc.app.controller.fin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.fin.FinAccount;
import com.cc.core.service.fin.FinAccountService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 资金账户控制器
 */
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class FinAccountController {

    private final FinAccountService accountService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<Page<FinAccount>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return R.ok(accountService.page(new Page<>(page, pageSize)));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<FinAccount> detail(@PathVariable Long id) {
        FinAccount account = accountService.getById(id);
        if (account == null) {
            return R.fail("资金账户不存在");
        }
        return R.ok(account);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody FinAccount account) {
        account.setStatus(0);
        if (account.getBalance() == null) {
            account.setBalance(java.math.BigDecimal.ZERO);
        }
        accountService.save(account);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody FinAccount account) {
        account.setId(id);
        accountService.updateById(account);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return accountService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
