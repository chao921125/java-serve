package com.cc.app.service.impl.bas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.bas.BasStore;
import com.cc.core.mapper.bas.BasStoreMapper;
import com.cc.core.service.bas.BasStoreService;
import org.springframework.stereotype.Service;

/**
 * BasStore 服务实现
 */
@Service
public class BasStoreServiceImpl extends ServiceImpl<BasStoreMapper, BasStore> implements BasStoreService {

    // ==== Business Logic Methods ====

    @Override
    public void toggleEnabled(Long id) {
        BasStore store = getById(id);
        store.setIsEnabled(store.getIsEnabled() == 1 ? 0 : 1);
        updateById(store);
    }

}
