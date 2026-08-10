package com.cc.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysPost;
import com.cc.core.mapper.SysPostMapper;
import com.cc.core.service.SysPostService;
import org.springframework.stereotype.Service;

/**
 * 岗位服务实现
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {
}
