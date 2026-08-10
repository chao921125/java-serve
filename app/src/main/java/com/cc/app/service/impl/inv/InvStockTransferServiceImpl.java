package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.inv.StockTransferQueryDTO;
import com.cc.core.entity.inv.InvStockTransfer;
import com.cc.core.entity.inv.InvStockTransferItem;
import com.cc.core.mapper.inv.InvStockTransferMapper;
import com.cc.core.service.inv.InvStockTransferItemService;
import com.cc.core.service.inv.InvStockTransferService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 调拨单服务实现
 */
@Service
@RequiredArgsConstructor
public class InvStockTransferServiceImpl extends ServiceImpl<InvStockTransferMapper, InvStockTransfer>
        implements InvStockTransferService {

    private final InvStockTransferItemService stockTransferItemService;
    private final InvInventoryServiceImpl inventoryService;

    @Override
    public IPage<InvStockTransfer> page(StockTransferQueryDTO query) {
        Page<InvStockTransfer> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<InvStockTransfer> wrapper = new LambdaQueryWrapper<>();
        if (query.getTransferNo() != null && !query.getTransferNo().isEmpty()) {
            wrapper.like(InvStockTransfer::getTransferNo, query.getTransferNo());
        }
        if (query.getFromWarehouseId() != null) {
            wrapper.eq(InvStockTransfer::getFromWarehouseId, query.getFromWarehouseId());
        }
        if (query.getToWarehouseId() != null) {
            wrapper.eq(InvStockTransfer::getToWarehouseId, query.getToWarehouseId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(InvStockTransfer::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(InvStockTransfer::getTransferDate, query.getStartDate());
        }
        if (query.getEndDate() != null) {
            wrapper.le(InvStockTransfer::getTransferDate, query.getEndDate());
        }
        wrapper.orderByDesc(InvStockTransfer::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        InvStockTransfer transfer = this.getById(id);
        if (transfer == null) {
            throw new RuntimeException("调拨单不存在");
        }
        if (transfer.getStatus() != 1) {
            throw new RuntimeException("只有待审状态的调拨单才能审核");
        }

        List<InvStockTransferItem> items = getItems(id);
        for (InvStockTransferItem item : items) {
            BigDecimal quantity = item.getQuantity();
            BigDecimal costPrice = item.getCostPrice() != null ? item.getCostPrice() : BigDecimal.ZERO;

            // 调出仓库扣库存，写流水 type=7
            inventoryService.stockOut(
                    transfer.getFromWarehouseId(), item.getProductId(), item.getBatchNo(),
                    quantity, 7, "STOCK_TRANSFER", transfer.getId(),
                    transfer.getTransferNo(), "调拨出库"
            );

            // 调入仓库加库存，写流水 type=8
            inventoryService.stockIn(
                    transfer.getToWarehouseId(), item.getProductId(), item.getBatchNo(),
                    quantity, costPrice, 8, "STOCK_TRANSFER", transfer.getId(),
                    transfer.getTransferNo(), "调拨入库"
            );
        }

        transfer.setStatus(3); // 已完成
        transfer.setApproverId(SecurityUtil.getUserId());
        transfer.setApproveTime(LocalDateTime.now());
        this.updateById(transfer);
    }

    @Override
    public List<InvStockTransferItem> getItems(Long transferId) {
        return stockTransferItemService.list(
                new LambdaQueryWrapper<InvStockTransferItem>()
                        .eq(InvStockTransferItem::getTransferId, transferId)
        );
    }
}
