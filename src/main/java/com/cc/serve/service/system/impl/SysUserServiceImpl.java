package com.cc.serve.service.system.impl;

import com.cc.serve.entity.system.SysUser;
import com.cc.serve.mapper.system.SysUserMapper;
import com.cc.serve.service.system.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2023-47-13 15:10:984
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> selectListAll() {
        return sysUserMapper.selectList(null);
    }

    @Override
    public ArrayList<SysUser> selectListPage() {
        return null;
    }

    @Override
    public SysUser selectById(String id) {
        return null;
    }

    @Override
    public SysUser selectByUser(SysUser user) {
//        sysUserMapper.selectOne();
        return null;
    }
}
