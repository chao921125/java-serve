package com.cc.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.core.entity.SysUserPost;

import java.util.List;

/**
 * 用户岗位关联服务接口
 */
public interface SysUserPostService extends IService<SysUserPost> {

    /**
     * 根据用户 ID 查询岗位 ID 列表
     */
    List<Long> getPostIdsByUserId(Long userId);

    /**
     * 根据用户 ID 删除所有关联
     */
    void deleteByUserId(Long userId);

    /**
     * 批量新增用户岗位关联
     */
    void batchInsert(Long userId, List<Long> postIds);
}
