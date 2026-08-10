package com.cc.core.service.bas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.bas.BasProductAttribute;

import java.util.List;

/**
 * 商品属性值关联服务接口
 */
public interface BasProductAttributeService extends IService<BasProductAttribute> {

    /**
     * 获取商品的全部属性值
     */
    List<BasProductAttribute> getByProductId(Long productId);

    /**
     * 批量保存商品属性
     */
    void batchSave(Long productId, List<BasProductAttribute> attributes);
}
