package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.pur.SupplierPriceQueryDTO;
import com.cc.core.entity.pur.PurSupplierPrice;
import com.cc.core.mapper.pur.PurSupplierPriceMapper;
import com.cc.core.service.pur.PurSupplierPriceService;
import org.springframework.stereotype.Service;

/**
 * 供应商价格服务实现
 */
@Service
public class PurSupplierPriceServiceImpl extends ServiceImpl<PurSupplierPriceMapper, PurSupplierPrice>
        implements PurSupplierPriceService {

    @Override
    public IPage<PurSupplierPrice> page(SupplierPriceQueryDTO query) {
        Page<PurSupplierPrice> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<PurSupplierPrice> wrapper = new LambdaQueryWrapper<>();
        if (query.getSupplierId() != null) {
            wrapper.eq(PurSupplierPrice::getSupplierId, query.getSupplierId());
        }
        if (query.getProductId() != null) {
            wrapper.eq(PurSupplierPrice::getProductId, query.getProductId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(PurSupplierPrice::getStatus, query.getStatus());
        }
        wrapper.orderByDesc(PurSupplierPrice::getCreateTime);
        return this.page(page, wrapper);
    }
}
