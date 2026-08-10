package com.cc.core.service.pur;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.pur.PurSupplierEvaluation;

/**
 * PurSupplierEvaluation 服务接口
 */
public interface PurSupplierEvaluationService extends IService<PurSupplierEvaluation> {


    /**
     * 计算综合评分（各维度加权平均）
     */
    void calculateTotalScore(Long id);

}
