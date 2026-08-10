package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasProductAttribute;
import com.cc.core.mapper.bas.BasProductAttributeMapper;
import com.cc.core.service.bas.BasProductAttributeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品属性值关联服务实现
 */
@Service
public class BasProductAttributeServiceImpl extends ServiceImpl<BasProductAttributeMapper, BasProductAttribute>
        implements BasProductAttributeService {

    @Override
    public List<BasProductAttribute> getByProductId(Long productId) {
        LambdaQueryWrapper<BasProductAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BasProductAttribute::getProductId, productId);
        return list(wrapper);
    }

    @Override
    @Transactional
    public void batchSave(Long productId, List<BasProductAttribute> attributes) {
        // 先删除旧的，再批量新增
        LambdaQueryWrapper<BasProductAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BasProductAttribute::getProductId, productId);
        remove(wrapper);

        if (attributes != null && !attributes.isEmpty()) {
            attributes.forEach(a -> a.setProductId(productId));
            saveBatch(attributes);
        }
    }
}
