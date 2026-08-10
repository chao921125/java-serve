package com.cc.core.service.pur;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.pur.SupplierPriceQueryDTO;
import com.cc.core.entity.pur.PurSupplierPrice;

/**
 * 供应商价格服务接口
 */
public interface PurSupplierPriceService extends IService<PurSupplierPrice> {

    /**
     * 分页查询供应商价格
     */
    IPage<PurSupplierPrice> page(SupplierPriceQueryDTO query);
}
