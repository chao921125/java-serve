package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysDictionary;
import com.cc.server.mapper.system.SysDictionaryMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysDictionaryService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements SysDictionaryService {
  @Autowired
  private SysDictionaryMapper sysDictionaryMapper;

 /**
 *  查询表sys_dictionary所有信息
 */
 @Override
 public List<SysDictionary> selectAllSysDictionary() { return sysDictionaryMapper.selectAllSysDictionary();}

   /**
   *  根据主键id查询表sys_dictionary信息
   *  @param id
   */
   @Override
   public SysDictionary selectSysDictionaryById(@Param("id") Integer id) { return sysDictionaryMapper.selectSysDictionaryById(id);}

 /**
 *  根据条件查询表sys_dictionary信息
 *  @param sysDictionary
 */
 @Override
 public List<SysDictionary> selectSysDictionaryByCondition(SysDictionary sysDictionary) { return sysDictionaryMapper.selectSysDictionaryByCondition(sysDictionary);}

   /**
   *  根据主键id查询表sys_dictionary信息
   *  @param id
   */
   @Override
   public Integer deleteSysDictionaryById(@Param("id") Integer id) { return sysDictionaryMapper.deleteSysDictionaryById(id);}

   /**
   *  根据主键id更新表sys_dictionary信息
   *  @param sysDictionary
   */
   @Override
   public Integer updateSysDictionaryById(SysDictionary sysDictionary) { return sysDictionaryMapper.updateSysDictionaryById(sysDictionary);}

   /**
   *  新增表sys_dictionary信息
   *  @param sysDictionary
   */
   @Override
   public Integer insertSysDictionary(SysDictionary sysDictionary) { return sysDictionaryMapper.insertSysDictionary(sysDictionary);}
}
