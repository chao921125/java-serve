package com.cc.core.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.sys.SysPrintTemplate;

/**
 * SysPrintTemplate 服务接口
 */
public interface SysPrintTemplateService extends IService<SysPrintTemplate> {

    /**
     * 预览打印
     */
    String preview(Long templateId, Long businessId);

    /**
     * 设为默认模板
     */
    void setDefault(Long templateId);
}
