package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasAttributeValue;
import com.cc.core.mapper.bas.BasAttributeValueMapper;
import com.cc.core.service.bas.BasAttributeValueService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 属性预设值服务实现
 */
@Service
public class BasAttributeValueServiceImpl extends ServiceImpl<BasAttributeValueMapper, BasAttributeValue>
        implements BasAttributeValueService {

    @Override
    public List<BasAttributeValue> listByAttributeId(Long attributeId) {
        LambdaQueryWrapper<BasAttributeValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BasAttributeValue::getAttributeId, attributeId);
        wrapper.orderByAsc(BasAttributeValue::getSortOrder);
        return list(wrapper);
    }
}
