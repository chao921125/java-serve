package com.cc.core.service.fin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.dto.fin.ReceivableQueryDTO;
import com.cc.core.entity.fin.FinReceivable;

/**
 * 应收账款服务接口
 */
public interface FinReceivableService extends IService<FinReceivable> {

    /**
     * 分页查询应收账款
     */
    IPage<FinReceivable> page(ReceivableQueryDTO query);
}
