package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.inv.StockTakeQueryDTO;
import com.cc.core.entity.inv.InvStockTake;
import com.cc.core.entity.inv.InvStockTakeItem;
import com.cc.core.mapper.inv.InvStockTakeMapper;
import com.cc.core.service.inv.InvStockTakeItemService;
import com.cc.core.service.inv.InvStockTakeService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 盘点单服务实现
 */
@Service
@RequiredArgsConstructor
public class InvStockTakeServiceImpl extends ServiceImpl<InvStockTakeMapper, InvStockTake>
        implements InvStockTakeService {

    private final InvStockTakeItemService stockTakeItemService;
    private final InvInventoryServiceImpl inventoryService;

    @Override
    public IPage<InvStockTake> page(StockTakeQueryDTO query) {
        Page<InvStockTake> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<InvStockTake> wrapper = new LambdaQueryWrapper<>();
        if (query.getTakeNo() != null && !query.getTakeNo().isEmpty()) {
            wrapper.like(InvStockTake::getTakeNo, query.getTakeNo());
        }
        if (query.getWarehouseId() != null) {
            wrapper.eq(InvStockTake::getWarehouseId, query.getWarehouseId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(InvStockTake::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(InvStockTake::getTakeDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(InvStockTake::getTakeDate, query.getEndDate());
        }
        wrapper.orderByDesc(InvStockTake::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        InvStockTake stockTake = this.getById(id);
        if (stockTake == null) {
            throw new RuntimeException("盘点单不存在");
        }
        if (stockTake.getStatus() != 2) {
            throw new RuntimeException("只有待审核状态的盘点单才能审核");
        }

        List<InvStockTakeItem> items = getItems(id);
        BigDecimal totalDiffQuantity = BigDecimal.ZERO;
        BigDecimal totalDiffAmount = BigDecimal.ZERO;

        for (InvStockTakeItem item : items) {
            BigDecimal bookQty = item.getBookQuantity() != null ? item.getBookQuantity() : BigDecimal.ZERO;
            BigDecimal actualQty = item.getActualQuantity() != null ? item.getActualQuantity() : BigDecimal.ZERO;
            BigDecimal diff = actualQty.subtract(bookQty);
            BigDecimal costPrice = item.getCostPrice() != null ? item.getCostPrice() : BigDecimal.ZERO;

            if (diff.compareTo(BigDecimal.ZERO) > 0) {
                // 盘盈：增加库存，写流水 type=5
                inventoryService.stockIn(
                        stockTake.getWarehouseId(), item.getProductId(), item.getBatchNo(),
                        diff, costPrice, 5, "STOCK_TAKE", stockTake.getId(),
                        stockTake.getTakeNo(), "盘盈入库"
                );
            } else if (diff.compareTo(BigDecimal.ZERO) < 0) {
                // 盘亏：减少库存，写流水 type=6
                inventoryService.stockOut(
                        stockTake.getWarehouseId(), item.getProductId(), item.getBatchNo(),
                        diff.negate(), 6, "STOCK_TAKE", stockTake.getId(),
                        stockTake.getTakeNo(), "盘亏出库"
                );
            }

            totalDiffQuantity = totalDiffQuantity.add(diff);
            totalDiffAmount = totalDiffAmount.add(diff.multiply(costPrice));
        }

        stockTake.setTotalDiffQuantity(totalDiffQuantity);
        stockTake.setTotalDiffAmount(totalDiffAmount);
        stockTake.setStatus(3); // 已完成
        stockTake.setApproverId(SecurityUtil.getUserId());
        stockTake.setApproveTime(LocalDateTime.now());
        this.updateById(stockTake);
    }

    @Override
    public List<InvStockTakeItem> getItems(Long takeId) {
        return stockTakeItemService.list(
                new LambdaQueryWrapper<InvStockTakeItem>()
                        .eq(InvStockTakeItem::getTakeId, takeId)
        );
    }
}
