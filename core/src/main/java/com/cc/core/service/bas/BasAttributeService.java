package com.cc.core.service.bas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.bas.BasAttribute;

import java.util.List;

/**
 * 属性定义服务接口
 */
public interface BasAttributeService extends IService<BasAttribute> {

    /**
     * 获取模板下的属性列表
     */
    List<BasAttribute> listByTemplateId(Long templateId);
}
