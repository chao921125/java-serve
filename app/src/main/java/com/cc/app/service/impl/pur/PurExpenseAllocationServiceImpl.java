package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurExpenseAllocation;
import com.cc.core.mapper.pur.PurExpenseAllocationMapper;
import com.cc.core.service.pur.PurExpenseAllocationService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.math.RoundingMode;
import java.math.BigDecimal;
import com.cc.core.service.pur.PurExpenseAllocationDetailService;
import com.cc.core.service.pur.PurPurchaseReceiptItemService;
import com.cc.core.entity.pur.PurExpenseAllocationDetail;
import com.cc.core.entity.pur.PurPurchaseReceiptItem;

/**
 * PurExpenseAllocation 服务实现
 */
@Service
@RequiredArgsConstructor
public class PurExpenseAllocationServiceImpl extends ServiceImpl<PurExpenseAllocationMapper, PurExpenseAllocation> implements PurExpenseAllocationService {
    private final PurExpenseAllocationDetailService detailService;
    private final com.cc.core.service.pur.PurPurchaseReceiptItemService receiptItemService;


    // ==== Business Logic Methods ====

    @Override
    public void allocate(Long id) {
        PurExpenseAllocation entity = getById(id);
        if (entity == null) throw new RuntimeException("费用分摊单不存在");
        if (entity.getStatus() != 0) throw new RuntimeException("仅待分摊状态可执行");

        // 查询关联入库单明细
        java.util.List<com.cc.core.entity.pur.PurPurchaseReceiptItem> items = receiptItemService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.cc.core.entity.pur.PurPurchaseReceiptItem>()
                .eq(com.cc.core.entity.pur.PurPurchaseReceiptItem::getReceiptId, entity.getReceiptId())
        );

        if (items.isEmpty()) throw new RuntimeException("入库单无明细数据");

        // 按金额比例分摊
        java.math.BigDecimal totalAmount = items.stream()
            .map(com.cc.core.entity.pur.PurPurchaseReceiptItem::getAmount)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        for (com.cc.core.entity.pur.PurPurchaseReceiptItem item : items) {
            java.math.BigDecimal ratio = item.getAmount().divide(totalAmount, 4, java.math.RoundingMode.HALF_UP);
            java.math.BigDecimal allocatedAmount = entity.getExpenseAmount().multiply(ratio);

            PurExpenseAllocationDetail detail = new PurExpenseAllocationDetail();
            detail.setAllocationId(id);
            detail.setReceiptItemId(item.getId());
            detail.setProductId(item.getProductId());
            detail.setQuantity(item.getQuantity());
            detail.setAmount(allocatedAmount);
            detailService.save(detail);
        }

        entity.setStatus(1);
        updateById(entity);
    }

    @Override
    public void reverse(Long id) {
        PurExpenseAllocation entity = getById(id);
        if (entity == null) throw new RuntimeException("费用分摊单不存在");
        if (entity.getStatus() != 1) throw new RuntimeException("仅已分摊状态可冲销");

        detailService.remove(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PurExpenseAllocationDetail>()
                .eq(PurExpenseAllocationDetail::getAllocationId, id)
        );

        entity.setStatus(2);
        updateById(entity);
    }

}
