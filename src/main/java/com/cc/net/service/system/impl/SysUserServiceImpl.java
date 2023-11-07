package com.cc.net.service.system.impl;

import com.cc.net.entity.system.SysUser;
import com.cc.net.mapper.system.SysUserMapper;
import com.cc.net.service.system.SysUserService;
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
    public SysUser selectOne(String id) {
        return null;
    }

    @Override
    public SysUser selectOneByName(String userName) {
        return null;
    }
}
