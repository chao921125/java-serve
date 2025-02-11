package com.cc.server.mapper.system;

import com.cc.server.model.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser selectByNameEmailPhone(@Param("name") String name);
}
