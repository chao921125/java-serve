package com.cc.app.service.impl.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.inv.InvAssembly;
import com.cc.core.entity.inv.InvAssemblyItem;
import com.cc.core.mapper.inv.InvAssemblyMapper;
import com.cc.core.service.inv.InvAssemblyItemService;
import com.cc.core.service.inv.InvAssemblyService;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 组装拆卸单服务实现
 */
@Service
@RequiredArgsConstructor
public class InvAssemblyServiceImpl extends ServiceImpl<InvAssemblyMapper, InvAssembly>
        implements InvAssemblyService {

    private final InvAssemblyItemService assemblyItemService;
    private final InvInventoryServiceImpl inventoryService;

    @Override
    public IPage<InvAssembly> page(Page<InvAssembly> page, Integer type, Integer status) {
        LambdaQueryWrapper<InvAssembly> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(InvAssembly::getType, type);
        }
        if (status != null) {
            wrapper.eq(InvAssembly::getStatus, status);
        }
        wrapper.orderByDesc(InvAssembly::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id) {
        InvAssembly assembly = this.getById(id);
        if (assembly == null) {
            throw new RuntimeException("组装拆卸单不存在");
        }
        if (assembly.getStatus() != 1) {
            throw new RuntimeException("只有待审状态的组装拆卸单才能审核");
        }

        List<InvAssemblyItem> items = getItems(id);
        Long warehouseId = assembly.getWarehouseId();
        BigDecimal productQty = assembly.getQuantity();

        if (assembly.getType() == 0) {
            // 组装：减少组件库存(type=9)，增加成品库存(type=11)
            for (InvAssemblyItem item : items) {
                inventoryService.stockOut(
                        warehouseId, item.getComponentProductId(), null,
                        item.getQuantity(), 9, "ASSEMBLY", assembly.getId(),
                        assembly.getAssemblyNo(), "组装领料"
                );
            }
            inventoryService.stockIn(
                    warehouseId, assembly.getProductId(), null,
                    productQty, BigDecimal.ZERO, 11, "ASSEMBLY",
                    assembly.getId(), assembly.getAssemblyNo(), "组装入库"
            );
        } else {
            // 拆卸：减少成品库存(type=10)，增加组件库存(type=12)
            inventoryService.stockOut(
                    warehouseId, assembly.getProductId(), null,
                    productQty, 10, "ASSEMBLY", assembly.getId(),
                    assembly.getAssemblyNo(), "拆卸出库"
            );
            for (InvAssemblyItem item : items) {
                inventoryService.stockIn(
                        warehouseId, item.getComponentProductId(), null,
                        item.getQuantity(), item.getCostPrice(), 12, "ASSEMBLY",
                        assembly.getId(), assembly.getAssemblyNo(), "拆卸入库"
                );
            }
        }

        assembly.setStatus(3); // 已完成
        assembly.setApproverId(SecurityUtil.getUserId());
        assembly.setApproveTime(LocalDateTime.now());
        this.updateById(assembly);
    }

    @Override
    public List<InvAssemblyItem> getItems(Long assemblyId) {
        return assemblyItemService.list(
                new LambdaQueryWrapper<InvAssemblyItem>()
                        .eq(InvAssemblyItem::getAssemblyId, assemblyId)
        );
    }
}
