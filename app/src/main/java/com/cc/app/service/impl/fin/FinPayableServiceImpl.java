package com.cc.app.service.impl.fin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.dto.fin.PayableQueryDTO;
import com.cc.core.entity.fin.FinPayable;
import com.cc.core.mapper.fin.FinPayableMapper;
import com.cc.core.service.fin.FinPayableService;
import org.springframework.stereotype.Service;

/**
 * 应付账款服务实现
 */
@Service
public class FinPayableServiceImpl extends ServiceImpl<FinPayableMapper, FinPayable>
        implements FinPayableService {

    @Override
    public IPage<FinPayable> page(PayableQueryDTO query) {
        Page<FinPayable> page = new Page<>(
                query.getPage() != null ? query.getPage() : 1,
                query.getPageSize() != null ? query.getPageSize() : 10
        );
        LambdaQueryWrapper<FinPayable> wrapper = new LambdaQueryWrapper<>();
        if (query.getSupplierId() != null) {
            wrapper.eq(FinPayable::getSupplierId, query.getSupplierId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(FinPayable::getStatus, query.getStatus());
        }
        if (query.getStartDate() != null) {
            wrapper.ge(FinPayable::getCreateTime, query.getStartDate().atStartOfDay());
        }
        if (query.getEndDate() != null) {
            wrapper.le(FinPayable::getCreateTime, query.getEndDate().atTime(23, 59, 59));
        }
        wrapper.orderByDesc(FinPayable::getCreateTime);
        return this.page(page, wrapper);
    }
}
