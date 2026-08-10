package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasProductCategory;
import com.cc.core.mapper.bas.BasProductCategoryMapper;
import com.cc.core.service.bas.BasProductCategoryService;
import org.springframework.stereotype.Service;

/**
 * 商品分类服务实现
 */
@Service
public class BasProductCategoryServiceImpl extends ServiceImpl<BasProductCategoryMapper, BasProductCategory> implements BasProductCategoryService {
}
