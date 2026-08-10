package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.fin.ReceivableQueryDTO;
import com.cc.core.entity.fin.FinReceivable;
import com.cc.core.mapper.fin.FinReceivableMapper;
import com.cc.core.service.fin.FinReceivableService;
import org.springframework.stereotype.Service;

/**
 * 应收账款服务实现
 */
@Service
public class FinReceivableServiceImpl extends ServiceImpl<FinReceivableMapper, FinReceivable>
        implements FinReceivableService {

    @Override
    public IPage<FinReceivable> page(ReceivableQueryDTO query) {
        Page<FinReceivable> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<FinReceivable> wrapper = new LambdaQueryWrapper<>();
        if (query.getCustomerId() != null) {
            wrapper.eq(FinReceivable::getCustomerId, query.getCustomerId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(FinReceivable::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(FinReceivable::getCreateTime, query.getStartDate().atStartOfDay());
        }
        if (query.getEndDate() != null) {
            wrapper.le(FinReceivable::getCreateTime, query.getEndDate().atTime(23, 59, 59));
        }
        wrapper.orderByDesc(FinReceivable::getCreateTime);
        return this.page(page, wrapper);
    }
}
