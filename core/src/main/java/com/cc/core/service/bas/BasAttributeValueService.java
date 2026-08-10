package com.cc.core.service.bas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.bas.BasAttributeValue;

import java.util.List;

/**
 * 属性预设值服务接口
 */
public interface BasAttributeValueService extends IService<BasAttributeValue> {

    /**
     * 获取属性下所有可选值
     */
    List<BasAttributeValue> listByAttributeId(Long attributeId);
}
