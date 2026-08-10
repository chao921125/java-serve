package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasAttribute;
import com.cc.core.mapper.bas.BasAttributeMapper;
import com.cc.core.service.bas.BasAttributeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 属性定义服务实现
 */
@Service
public class BasAttributeServiceImpl extends ServiceImpl<BasAttributeMapper, BasAttribute> implements BasAttributeService {

    @Override
    public List<BasAttribute> listByTemplateId(Long templateId) {
        LambdaQueryWrapper<BasAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BasAttribute::getTemplateId, templateId);
        wrapper.orderByAsc(BasAttribute::getSortOrder);
        return list(wrapper);
    }
}
