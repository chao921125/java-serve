package com.cc.serve.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.serve.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2024-09-28 15:03:228
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    public List<SysUser> listPage(SysUser sysUser);
}
