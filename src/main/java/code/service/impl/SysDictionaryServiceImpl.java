package service.impl;

import entity.SysDictionary;
import java.util.List;
import mapper.SysDictionaryMapper;
    import service.SysDictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    *  服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements SysDictionaryService {
    @Resource
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
            public SysDictionary selectSysDictionaryByid(@Param("id") Integer id) { return sysDictionaryMapper.selectSysDictionaryByid(id);}

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
            public Integer deleteSysDictionaryByid(@Param("id") Integer id) { return sysDictionaryMapper.deleteSysDictionaryByid(id);}

            /**
            *  根据主键id更新表sys_dictionary信息
            *  @param sysDictionary
            */
            @Override
            public Integer updateSysDictionaryByid(SysDictionary sysDictionary) { return sysDictionaryMapper.updateSysDictionaryByid(sysDictionary);}

            /**
            *  新增表sys_dictionary信息
            *  @param sysDictionary
            */
            @Override
            public Integer insertSysDictionary(SysDictionary sysDictionary) { return sysDictionaryMapper.insertSysDictionary(sysDictionary);}
    }
