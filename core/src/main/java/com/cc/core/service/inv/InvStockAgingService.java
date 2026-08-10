package com.cc.core.service.inv;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.inv.InvStockAging;

/**
 * InvStockAging 服务接口
 */
public interface InvStockAgingService extends IService<InvStockAging> {


    /**
     * 生成库龄快照
     */
    void generateSnapshot();

    /**
     * 获取呆滞品列表
     */
    com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cc.core.entity.inv.InvStockAging> getSlowMoving(com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cc.core.entity.inv.InvStockAging> page);

    /**
     * 获取周转率统计
     */
    java.util.List<java.util.Map<String, Object>> getTurnoverRate(Integer days);

}
