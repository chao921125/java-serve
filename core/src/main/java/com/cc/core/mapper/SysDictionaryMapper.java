package com.cc.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.core.entity.SysDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 字典 Mapper
 */
@Mapper
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    @Select("SELECT * FROM sys_dictionary WHERE name = #{name} AND status = 0 LIMIT 1")
    SysDictionary selectByName(@Param("name") String name);
}
