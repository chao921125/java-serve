package com.cc.javaserve.service.impl;

import com.cc.javaserve.entity.SysUser;
import com.cc.javaserve.mapper.SysUserMapper;
import com.cc.javaserve.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2023-28-27 10:09:265
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
