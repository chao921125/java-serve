package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasAttributeTemplate;
import com.cc.core.mapper.bas.BasAttributeTemplateMapper;
import com.cc.core.service.bas.BasAttributeTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 属性模板服务实现
 */
@Service
public class BasAttributeTemplateServiceImpl extends ServiceImpl<BasAttributeTemplateMapper, BasAttributeTemplate>
        implements BasAttributeTemplateService {

    @Override
    @Transactional
    public void toggleEnabled(Long id) {
        BasAttributeTemplate template = getById(id);
        if (template != null) {
            template.setIsEnabled(template.getIsEnabled() == 1 ? 0 : 1);
            updateById(template);
        }
    }
}
