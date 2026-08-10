package com.cc.core.service.bas;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.bas.ProductQueryDTO;
import com.cc.core.entity.bas.BasProduct;

/**
 * 商品服务接口
 */
public interface BasProductService extends IService<BasProduct> {

    /**
     * 分页查询商品
     */
    IPage<BasProduct> page(ProductQueryDTO query);

    /**
     * 修改状态
     */
    void updateStatus(Long id, Integer status);
}
