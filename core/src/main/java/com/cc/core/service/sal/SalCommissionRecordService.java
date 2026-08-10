package com.cc.core.service.sal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sal.SalCommissionRecord;

/**
 * SalCommissionRecord 服务接口
 */
public interface SalCommissionRecordService extends IService<SalCommissionRecord> {


    /**
     * 计算指定周期的提成
     */
    void calculate(String period);

    /**
     * 发放提成
     */
    void pay(Long id);

}
