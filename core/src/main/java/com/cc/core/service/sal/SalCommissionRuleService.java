package com.cc.core.service.sal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sal.SalCommissionRule;

/**
 * SalCommissionRule 服务接口
 */
public interface SalCommissionRuleService extends IService<SalCommissionRule> {


    /**
     * 切换启用/停用状态
     */
    void toggleEnabled(Long id);

    /**
     * 根据规则计算提成金额
     */
    java.math.BigDecimal calculateCommission(Long ruleId, java.math.BigDecimal baseAmount);

}
