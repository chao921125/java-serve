package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.bas.ProductQueryDTO;
import com.cc.core.entity.bas.BasProduct;
import com.cc.core.mapper.bas.BasProductMapper;
import com.cc.core.service.bas.BasProductService;
import org.springframework.stereotype.Service;

/**
 * 商品服务实现
 */
@Service
public class BasProductServiceImpl extends ServiceImpl<BasProductMapper, BasProduct> implements BasProductService {

    @Override
    public IPage<BasProduct> page(ProductQueryDTO query) {
        Page<BasProduct> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<BasProduct> wrapper = new LambdaQueryWrapper<>();
        if (query.getProductCode() != null && !query.getProductCode().isEmpty()) {
            wrapper.like(BasProduct::getProductCode, query.getProductCode());
        }
        if (query.getName() != null && !query.getName().isEmpty()) {
            wrapper.like(BasProduct::getName, query.getName());
        }
        if (query.getCategoryId() != null) {
            wrapper.eq(BasProduct::getCategoryId, query.getCategoryId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(BasProduct::getStatus, query.getStatus());
        }
        wrapper.orderByDesc(BasProduct::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        BasProduct entity = new BasProduct();
        entity.setId(id);
        entity.setStatus(status);
        this.updateById(entity);
    }
}
