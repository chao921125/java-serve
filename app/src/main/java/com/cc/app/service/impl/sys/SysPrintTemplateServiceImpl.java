package com.cc.app.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.sys.SysPrintTemplate;
import com.cc.core.mapper.sys.SysPrintTemplateMapper;
import com.cc.core.service.sys.SysPrintTemplateService;
import org.springframework.stereotype.Service;

/**
 * SysPrintTemplate 服务实现
 */
@Service
public class SysPrintTemplateServiceImpl extends ServiceImpl<SysPrintTemplateMapper, SysPrintTemplate> implements SysPrintTemplateService {

    @Override
    public String preview(Long templateId, Long businessId) {
        SysPrintTemplate template = getById(templateId);
        if (template == null) return "";
        return template.getTemplateContent();
    }

    @Override
    public void setDefault(Long templateId) {
        SysPrintTemplate template = getById(templateId);
        if (template == null) return;
        lambdaUpdate().eq(SysPrintTemplate::getBusinessType, template.getBusinessType())
                .eq(SysPrintTemplate::getIsDefault, 1)
                .set(SysPrintTemplate::getIsDefault, 0)
                .update();
        template.setIsDefault(1);
        updateById(template);
    }
}
