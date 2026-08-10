package com.cc.core.mapper.bas;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.bas.BasProductAttribute;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性值关联 Mapper
 */
@Mapper
public interface BasProductAttributeMapper extends BaseMapper<BasProductAttribute> {
}
