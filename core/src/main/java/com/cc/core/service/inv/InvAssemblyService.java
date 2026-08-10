package com.cc.core.service.inv;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.inv.InvAssembly;
import com.cc.core.entity.inv.InvAssemblyItem;

import java.util.List;

/**
 * 组装拆卸单服务接口
 */
public interface InvAssemblyService extends IService<InvAssembly> {

    /**
     * 分页查询组装拆卸单
     */
    IPage<InvAssembly> page(Page<InvAssembly> page, Integer type, Integer status);

    /**
     * 审核组装拆卸单
     */
    void approve(Long id);

    /**
     * 获取组装拆卸单明细
     */
    List<InvAssemblyItem> getItems(Long assemblyId);
}
