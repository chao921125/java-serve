package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasBrand;
import com.cc.core.mapper.bas.BasBrandMapper;
import com.cc.core.service.bas.BasBrandService;
import org.springframework.stereotype.Service;

/**
 * 品牌服务实现
 */
@Service
public class BasBrandServiceImpl extends ServiceImpl<BasBrandMapper, BasBrand> implements BasBrandService {
}
