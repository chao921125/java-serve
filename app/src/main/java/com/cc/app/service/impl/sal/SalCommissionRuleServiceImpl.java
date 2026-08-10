package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sal.SalCommissionRule;
import com.cc.core.mapper.sal.SalCommissionRuleMapper;
import com.cc.core.service.sal.SalCommissionRuleService;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;
import java.math.BigDecimal;

/**
 * SalCommissionRule 服务实现
 */
@Service
public class SalCommissionRuleServiceImpl extends ServiceImpl<SalCommissionRuleMapper, SalCommissionRule> implements SalCommissionRuleService {

    // ==== Business Logic Methods ====

    @Override
    public void toggleEnabled(Long id) {
        com.cc.core.entity.sal.SalCommissionRule rule = getById(id);
        rule.setIsEnabled(rule.getIsEnabled() == 1 ? 0 : 1);
        updateById(rule);
    }

    @Override
    public java.math.BigDecimal calculateCommission(Long ruleId, java.math.BigDecimal baseAmount) {
        com.cc.core.entity.sal.SalCommissionRule rule = getById(ruleId);
        if (rule == null || rule.getIsEnabled() != 1) return java.math.BigDecimal.ZERO;
        if (rule.getMinThreshold() != null && baseAmount.compareTo(rule.getMinThreshold()) < 0) return java.math.BigDecimal.ZERO;

        java.math.BigDecimal commission;
        if ("RATE".equals(rule.getCalculationMethod())) {
            commission = baseAmount.multiply(rule.getCommissionRate()).divide(new java.math.BigDecimal(100), 2, java.math.RoundingMode.HALF_UP);
        } else if ("FIXED".equals(rule.getCalculationMethod())) {
            commission = rule.getFixedAmount() != null ? rule.getFixedAmount() : java.math.BigDecimal.ZERO;
        } else {
            commission = baseAmount.multiply(rule.getCommissionRate()).divide(new java.math.BigDecimal(100), 2, java.math.RoundingMode.HALF_UP);
        }

        if (rule.getMaxCap() != null && commission.compareTo(rule.getMaxCap()) > 0) {
            commission = rule.getMaxCap();
        }
        return commission;
    }

}
