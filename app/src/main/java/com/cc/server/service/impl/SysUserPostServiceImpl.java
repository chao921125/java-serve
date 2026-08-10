package com.cc.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysUserPost;
import com.cc.core.mapper.SysUserPostMapper;
import com.cc.core.service.SysUserPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户岗位关联服务实现
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostService {

    @Override
    public List<Long> getPostIdsByUserId(Long userId) {
        List<SysUserPost> list = baseMapper.selectByUserId(userId);
        return list.stream().map(SysUserPost::getPostId).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        baseMapper.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void batchInsert(Long userId, List<Long> postIds) {
        if (postIds == null || postIds.isEmpty()) return;
        List<SysUserPost> list = new ArrayList<>();
        for (Long postId : postIds) {
            SysUserPost up = new SysUserPost();
            up.setUserId(userId);
            up.setPostId(postId);
            list.add(up);
        }
        saveBatch(list);
    }
}
