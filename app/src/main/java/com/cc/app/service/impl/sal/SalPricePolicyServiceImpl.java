package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.sal.PricePolicyQueryDTO;
import com.cc.core.dto.sal.PricePolicySaveDTO;
import com.cc.core.entity.sal.SalPricePolicy;
import com.cc.core.mapper.sal.SalPricePolicyMapper;
import com.cc.core.service.sal.SalPricePolicyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 价格策略服务实现
 */
@Service
public class SalPricePolicyServiceImpl extends ServiceImpl<SalPricePolicyMapper, SalPricePolicy>
        implements SalPricePolicyService {

    @Override
    public IPage<SalPricePolicy> page(PricePolicyQueryDTO query) {
        Page<SalPricePolicy> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<SalPricePolicy> wrapper = new LambdaQueryWrapper<>();
        if (query.getName() != null && !query.getName().isEmpty()) {
            wrapper.like(SalPricePolicy::getName, query.getName());
        }
        if (query.getType() != null) {
            wrapper.eq(SalPricePolicy::getType, query.getType());
        }
        if (query.getCustomerId() != null) {
            wrapper.eq(SalPricePolicy::getCustomerId, query.getCustomerId());
        }
        if (query.getProductId() != null) {
            wrapper.eq(SalPricePolicy::getProductId, query.getProductId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(SalPricePolicy::getStatus, query.getStatus());
        }
        wrapper.orderByDesc(SalPricePolicy::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PricePolicySaveDTO dto) {
        SalPricePolicy policy = new SalPricePolicy();
        policy.setName(dto.getName());
        policy.setType(dto.getType());
        policy.setCustomerId(dto.getCustomerId());
        policy.setCustomerLevel(dto.getCustomerLevel());
        policy.setProductId(dto.getProductId());
        policy.setCategoryId(dto.getCategoryId());
        policy.setDiscountRate(dto.getDiscountRate());
        policy.setFixedPrice(dto.getFixedPrice());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        this.save(policy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, PricePolicySaveDTO dto) {
        SalPricePolicy policy = this.getById(id);
        if (policy == null) {
            throw new RuntimeException("价格策略不存在");
        }
        policy.setName(dto.getName());
        policy.setType(dto.getType());
        policy.setCustomerId(dto.getCustomerId());
        policy.setCustomerLevel(dto.getCustomerLevel());
        policy.setProductId(dto.getProductId());
        policy.setCategoryId(dto.getCategoryId());
        policy.setDiscountRate(dto.getDiscountRate());
        policy.setFixedPrice(dto.getFixedPrice());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setStatus(dto.getStatus());
        this.updateById(policy);
    }
}
