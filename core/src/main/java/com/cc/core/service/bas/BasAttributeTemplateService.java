package com.cc.core.service.bas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.bas.BasAttributeTemplate;

/**
 * 属性模板服务接口
 */
public interface BasAttributeTemplateService extends IService<BasAttributeTemplate> {

    /**
     * 修改启用状态
     */
    void toggleEnabled(Long id);
}
