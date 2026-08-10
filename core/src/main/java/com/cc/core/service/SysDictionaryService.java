package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysDictionary;

/**
 * 字典服务接口
 */
public interface SysDictionaryService extends IService<SysDictionary> {

    /**
     * 根据名称获取字典值
     */
    String getValueByName(String name);
}
