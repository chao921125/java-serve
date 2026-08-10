package com.cc.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysDictionary;
import com.cc.core.mapper.SysDictionaryMapper;
import com.cc.core.service.SysDictionaryService;
import com.cc.framework.exception.ServiceException;
import org.springframework.stereotype.Service;

/**
 * 字典服务实现
 */
@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements SysDictionaryService {

    @Override
    public String getValueByName(String name) {
        SysDictionary dict = baseMapper.selectByName(name);
        return dict != null ? dict.getValue() : null;
    }
}
