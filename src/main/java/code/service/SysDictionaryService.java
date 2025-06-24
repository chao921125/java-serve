package service;

import entity.SysDictionary;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
    *  服务类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysDictionaryService extends IService<SysDictionary> {
    /**
    *  查询表sys_dictionary所有信息
    */
    List<SysDictionary> selectAllSysDictionary();

            /**
            *  根据主键id查询表sys_dictionary信息
            *  @param id
            */
            SysDictionary selectSysDictionaryByid(@Param("id") Integer id);

    /**
    *  根据条件查询表sys_dictionary信息
    *  @param sysDictionary
    */
    List<SysDictionary> selectSysDictionaryByCondition(SysDictionary sysDictionary);

            /**
            *  根据主键id查询表sys_dictionary信息
            *  @param id
            */
            Integer deleteSysDictionaryByid(@Param("id") Integer id);

            /**
            *  根据主键id更新表sys_dictionary信息
            *  @param sysDictionary
            */
            Integer updateSysDictionaryByid(SysDictionary sysDictionary);

            /**
            *  新增表sys_dictionary信息
            *  @param sysDictionary
            */
            Integer insertSysDictionary(SysDictionary sysDictionary);
    }
