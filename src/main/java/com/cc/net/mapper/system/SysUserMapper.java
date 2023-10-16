package com.cc.net.mapper.system;

import com.cc.net.entity.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2023-47-13 15:10:984
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
