package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.SysUserPost;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户岗位关联 Mapper
 */
@Mapper
public interface SysUserPostMapper extends BaseMapper<SysUserPost> {

    @Select("SELECT * FROM sys_user_post WHERE user_id = #{userId}")
    List<SysUserPost> selectByUserId(@Param("userId") Long userId);

    @Delete("DELETE FROM sys_user_post WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);
}
