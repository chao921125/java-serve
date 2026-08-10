package com.cc.core.service.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.fin.PayableQueryDTO;
import com.cc.core.entity.fin.FinPayable;

/**
 * 应付账款服务接口
 */
public interface FinPayableService extends IService<FinPayable> {

    /**
     * 分页查询应付账款
     */
    IPage<FinPayable> page(PayableQueryDTO query);
}
