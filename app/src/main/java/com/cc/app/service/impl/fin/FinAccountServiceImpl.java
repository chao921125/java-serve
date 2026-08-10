package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.fin.FinAccount;
import com.cc.core.mapper.fin.FinAccountMapper;
import com.cc.core.service.fin.FinAccountService;
import com.cc.core.service.fin.FinAccountTransactionService;
import com.cc.core.entity.fin.FinAccountTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户服务实现
 */
@Service
@RequiredArgsConstructor
public class FinAccountServiceImpl extends ServiceImpl<FinAccountMapper, FinAccount>
        implements FinAccountService {

    private final FinAccountTransactionService accountTransactionService;

    /**
     * 账户收入 — 增加余额并写流水
     *
     * @param accountId   账户 ID
     * @param amount      金额
     * @param sourceType  来源类型
     * @param sourceId    来源 ID
     * @param sourceNo    来源单号
     * @param remark      备注
     */
    @Transactional(rollbackFor = Exception.class)
    public void accountIn(Long accountId, BigDecimal amount, String sourceType,
                          Long sourceId, String sourceNo, String remark) {
        FinAccount account = this.getById(accountId);
        if (account == null) {
            throw new RuntimeException("资金账户不存在");
        }
        BigDecimal balanceBefore = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;
        BigDecimal balanceAfter = balanceBefore.add(amount);
        account.setBalance(balanceAfter);
        this.updateById(account);

        recordTransaction(accountId, 0, amount, balanceBefore, balanceAfter,
                sourceType, sourceId, sourceNo, remark);
    }

    /**
     * 账户支出 — 扣减余额并写流水
     *
     * @param accountId   账户 ID
     * @param amount      金额
     * @param sourceType  来源类型
     * @param sourceId    来源 ID
     * @param sourceNo    来源单号
     * @param remark      备注
     */
    @Transactional(rollbackFor = Exception.class)
    public void accountOut(Long accountId, BigDecimal amount, String sourceType,
                           Long sourceId, String sourceNo, String remark) {
        FinAccount account = this.getById(accountId);
        if (account == null) {
            throw new RuntimeException("资金账户不存在");
        }
        BigDecimal balanceBefore = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;
        BigDecimal balanceAfter = balanceBefore.subtract(amount);
        if (balanceAfter.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("账户余额不足");
        }
        account.setBalance(balanceAfter);
        this.updateById(account);

        recordTransaction(accountId, 1, amount, balanceBefore, balanceAfter,
                sourceType, sourceId, sourceNo, remark);
    }

    /**
     * 记录账户流水
     */
    private void recordTransaction(Long accountId, Integer transactionType, BigDecimal amount,
                                   BigDecimal balanceBefore, BigDecimal balanceAfter,
                                   String sourceType, Long sourceId, String sourceNo,
                                   String remark) {
        FinAccountTransaction transaction = new FinAccountTransaction();
        transaction.setAccountId(accountId);
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(balanceAfter);
        transaction.setSourceType(sourceType);
        transaction.setSourceId(sourceId);
        transaction.setSourceNo(sourceNo);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setRemark(remark);
        accountTransactionService.save(transaction);
    }
}
