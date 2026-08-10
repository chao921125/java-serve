package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.fin.ReceiptQueryDTO;
import com.cc.core.entity.fin.FinReceipt;
import com.cc.core.entity.fin.FinReceiptItem;
import com.cc.core.entity.fin.FinReceivable;
import com.cc.core.mapper.fin.FinReceiptMapper;
import com.cc.core.service.fin.FinReceiptItemService;
import com.cc.core.service.fin.FinReceiptService;
import com.cc.core.service.fin.FinReceivableService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 收款单服务实现
 */
@Service
@RequiredArgsConstructor
public class FinReceiptServiceImpl extends ServiceImpl<FinReceiptMapper, FinReceipt>
        implements FinReceiptService {

    private final FinReceiptItemService receiptItemService;
    private final FinReceivableService receivableService;
    private final FinAccountServiceImpl accountService;

    @Override
    public IPage<FinReceipt> page(ReceiptQueryDTO query) {
        Page<FinReceipt> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<FinReceipt> wrapper = new LambdaQueryWrapper<>();
        if (query.getReceiptNo() != null && !query.getReceiptNo().isEmpty()) {
            wrapper.like(FinReceipt::getReceiptNo, query.getReceiptNo());
        }
        if (query.getCustomerId() != null) {
            wrapper.eq(FinReceipt::getCustomerId, query.getCustomerId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(FinReceipt::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(FinReceipt::getReceiptDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(FinReceipt::getReceiptDate, query.getEndDate());
        }
        wrapper.orderByDesc(FinReceipt::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        FinReceipt receipt = this.getById(id);
        if (receipt == null) {
            throw new RuntimeException("收款单不存在");
        }
        if (receipt.getStatus() != 1) {
            throw new RuntimeException("只有待审状态的收款单才能审核");
        }

        List<FinReceiptItem> items = getItems(id);
        for (FinReceiptItem item : items) {
            FinReceivable receivable = receivableService.getById(item.getReceivableId());
            if (receivable == null) {
                throw new RuntimeException("应收账款不存在: " + item.getReceivableId());
            }

            BigDecimal receivedAmount = receivable.getReceivedAmount() != null
                    ? receivable.getReceivedAmount() : BigDecimal.ZERO;
            BigDecimal newReceivedAmount = receivedAmount.add(item.getAmount());
            BigDecimal newBalance = receivable.getAmount().subtract(newReceivedAmount);

            receivable.setReceivedAmount(newReceivedAmount);
            receivable.setBalance(newBalance);
            if (newBalance.compareTo(BigDecimal.ZERO) <= 0) {
                receivable.setStatus(2); // 已核销
            } else {
                receivable.setStatus(1); // 部分核销
            }
            receivableService.updateById(receivable);
        }

        // 账户收入
        accountService.accountIn(
                receipt.getAccountId(), receipt.getAmount(),
                "RECEIPT", receipt.getId(), receipt.getReceiptNo(),
                "收款单审核：" + receipt.getReceiptNo()
        );

        receipt.setStatus(2); // 已审
        receipt.setApproverId(SecurityUtil.getUserId());
        receipt.setApproveTime(LocalDateTime.now());
        this.updateById(receipt);
    }

    @Override
    public List<FinReceiptItem> getItems(Long receiptId) {
        return receiptItemService.list(
                new LambdaQueryWrapper<FinReceiptItem>()
                        .eq(FinReceiptItem::getReceiptId, receiptId)
        );
    }
}
