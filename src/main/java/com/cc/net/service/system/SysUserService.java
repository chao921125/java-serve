package com.cc.net.service.system;

import com.cc.net.entity.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cc
 * @since 2023-47-13 15:10:984
 */
public interface SysUserService extends IService<SysUser> {

    List<SysUser> selectListAll();

    List<SysUser> selectListPage();

    SysUser selectOne(String id);

    SysUser selectOneByName(String userName);
}
