package com.cc.net.service.system;

import com.cc.net.entity.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.net.mapper.system.SysUserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cc
 * @since 2023-47-13 15:10:984
 */
public interface SysUserService extends IService<SysUser> {

    @Autowired
    SysUserMapper sysUserMapper;

    public static ArrayList<SysUser> selectListPage() {
        return sysUserMapper.selectPage();
    }

    public static SysUser selectOne(String id) {
        return new SysUser();
    }

    public static SysUser selectOneByName(String userName) {
        return new SysUser();
    }
}
