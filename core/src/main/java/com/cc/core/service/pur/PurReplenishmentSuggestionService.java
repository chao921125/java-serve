package com.cc.core.service.pur;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.pur.PurReplenishmentSuggestion;

/**
 * PurReplenishmentSuggestion 服务接口
 */
public interface PurReplenishmentSuggestionService extends IService<PurReplenishmentSuggestion> {


    /**
     * 扫描库存并生成补货建议
     */
    int scanAndGenerate();

    /**
     * 将补货建议转为请购单
     */
    Long convertToRequisition(Long id);

    /**
     * 忽略建议
     */
    void ignore(Long id);

}
