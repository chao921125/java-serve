package com.cc.core.service.inv;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.inv.InvExpiryAlert;

/**
 * InvExpiryAlert 服务接口
 */
public interface InvExpiryAlertService extends IService<InvExpiryAlert> {


    /**
     * 扫描库存生成保质期预警
     */
    int scanAndAlert();

    /**
     * 处理预警
     */
    void handle(Long id, String handleMethod);

    /**
     * 获取预警统计
     */
    java.util.Map<String, Object> getStats();

}
