package com.cc.app.service.impl.sal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sal.SalCommissionRecord;
import com.cc.core.mapper.sal.SalCommissionRecordMapper;
import com.cc.core.service.sal.SalCommissionRecordService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.cc.core.service.sal.SalCommissionRuleService;
import com.cc.core.service.sal.SalCommissionRuleTargetService;
import com.cc.core.entity.sal.SalCommissionRule;
import com.cc.core.entity.sal.SalCommissionRuleTarget;

/**
 * SalCommissionRecord 服务实现
 */
@Service
@RequiredArgsConstructor
public class SalCommissionRecordServiceImpl extends ServiceImpl<SalCommissionRecordMapper, SalCommissionRecord> implements SalCommissionRecordService {
    private final SalCommissionRuleService commissionRuleService;
    private final SalCommissionRuleTargetService commissionRuleTargetService;


    // ==== Business Logic Methods ====

    @Override
    public void calculate(String period) {
        // 查询活跃规则
        java.util.List<com.cc.core.entity.sal.SalCommissionRule> rules = commissionRuleService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.cc.core.entity.sal.SalCommissionRule>()
                .eq(com.cc.core.entity.sal.SalCommissionRule::getIsEnabled, 1)
        );
        for (com.cc.core.entity.sal.SalCommissionRule rule : rules) {
            java.util.List<com.cc.core.entity.sal.SalCommissionRuleTarget> targets = commissionRuleTargetService.list(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<com.cc.core.entity.sal.SalCommissionRuleTarget>()
                    .eq(com.cc.core.entity.sal.SalCommissionRuleTarget::getRuleId, rule.getId())
            );
            for (com.cc.core.entity.sal.SalCommissionRuleTarget target : targets) {
                com.cc.core.entity.sal.SalCommissionRecord record = new com.cc.core.entity.sal.SalCommissionRecord();
                record.setUserId(target.getTargetId());
                record.setRuleId(rule.getId());
                record.setPeriod(period);
                record.setStatus(0);
                record.setBaseAmount(java.math.BigDecimal.ZERO);
                record.setCommissionAmount(java.math.BigDecimal.ZERO);
                record.setCalculatedTime(java.time.LocalDateTime.now().toString());
                save(record);
            }
        }
    }

    @Override
    public void pay(Long id) {
        com.cc.core.entity.sal.SalCommissionRecord record = getById(id);
        if (record.getStatus() != 0) throw new RuntimeException("仅待发放状态可发放");
        record.setStatus(1);
        record.setPaidTime(java.time.LocalDateTime.now().toString());
        updateById(record);
    }

}
