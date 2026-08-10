package com.cc.app.service.impl.pur;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.pur.PurSupplierEvaluation;
import com.cc.core.mapper.pur.PurSupplierEvaluationMapper;
import com.cc.core.service.pur.PurSupplierEvaluationService;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;
import java.math.BigDecimal;

/**
 * PurSupplierEvaluation 服务实现
 */
@Service
public class PurSupplierEvaluationServiceImpl extends ServiceImpl<PurSupplierEvaluationMapper, PurSupplierEvaluation> implements PurSupplierEvaluationService {

    // ==== Business Logic Methods ====

    @Override
    public void calculateTotalScore(Long id) {
        PurSupplierEvaluation eval = getById(id);
        if (eval == null) throw new RuntimeException("评估记录不存在");
        // 等权平均
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        int count = 0;
        if (eval.getQualityScore() != null) { total = total.add(eval.getQualityScore()); count++; }
        if (eval.getDeliveryScore() != null) { total = total.add(eval.getDeliveryScore()); count++; }
        if (eval.getPriceScore() != null) { total = total.add(eval.getPriceScore()); count++; }
        if (eval.getServiceScore() != null) { total = total.add(eval.getServiceScore()); count++; }
        if (count > 0) {
            eval.setTotalScore(total.divide(new java.math.BigDecimal(count), 2, java.math.RoundingMode.HALF_UP));
        }
        updateById(eval);
    }

}
