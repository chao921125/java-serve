package com.cc.app.controller.sal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.sal.PricePolicyQueryDTO;
import com.cc.core.dto.sal.PricePolicySaveDTO;
import com.cc.core.entity.sal.SalPricePolicy;
import com.cc.core.service.sal.SalPricePolicyService;
import com.cc.framework.base.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 价格策略控制器
 */
@RestController
@RequestMapping("/api/v1/price-policies")
@RequiredArgsConstructor
public class SalPricePolicyController {

    private final SalPricePolicyService pricePolicyService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<SalPricePolicy>> list(PricePolicyQueryDTO query) {
        return R.ok(pricePolicyService.page(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<SalPricePolicy> detail(@PathVariable Long id) {
        SalPricePolicy policy = pricePolicyService.getById(id);
        if (policy == null) {
            return R.fail("价格策略不存在");
        }
        return R.ok(policy);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@Valid @RequestBody PricePolicySaveDTO dto) {
        pricePolicyService.create(dto);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody PricePolicySaveDTO dto) {
        pricePolicyService.update(id, dto);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return pricePolicyService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
