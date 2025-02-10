package com.cc.server.service.system.impl;

import com.cc.server.model.system.entity.SysUser;
import com.cc.server.mapper.system.SysUserMapper;
import com.cc.server.service.system.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private SysUserService userService;

    @Override
    public SysUser getUserByNameEmailPhone(String username) {
        SysUser user = userService.getUserByNameEmailPhone(username);
        if (user == null) {
            return null;
//            throw new UsernameNotFoundException("用户不存在");
        } else {
            // 这里可以同时加载用户角色、权限信息，构造 SecurityUser
//        return new SecurityUser(user);
            return user;
        }
    }

    @Override
    public SysUser loginUser(String username, String password) {
        return null;
    }
}
