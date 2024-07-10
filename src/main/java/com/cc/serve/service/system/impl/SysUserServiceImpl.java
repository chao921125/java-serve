package com.cc.serve.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.serve.entity.SysUser;
import com.cc.serve.mapper.system.SysUserMapper;
import com.cc.serve.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2024-09-28 15:03:228
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<SysUser> listPage(SysUser sysUser) {
        return userMapper.listPage(sysUser);
    }
}
