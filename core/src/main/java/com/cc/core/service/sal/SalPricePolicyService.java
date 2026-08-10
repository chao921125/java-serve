package com.cc.core.service.sal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.sal.PricePolicyQueryDTO;
import com.cc.core.dto.sal.PricePolicySaveDTO;
import com.cc.core.entity.sal.SalPricePolicy;

/**
 * 价格策略服务接口
 */
public interface SalPricePolicyService extends IService<SalPricePolicy> {

    /**
     * 分页查询价格策略
     */
    IPage<SalPricePolicy> page(PricePolicyQueryDTO query);

    /**
     * 创建价格策略
     */
    void create(PricePolicySaveDTO dto);

    /**
     * 更新价格策略
     */
    void update(Long id, PricePolicySaveDTO dto);
}
