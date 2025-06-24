package service.impl;

import entity.SysPost;
import java.util.List;
import mapper.SysPostMapper;
    import service.SysPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 岗位表 服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {
    @Resource
    private SysPostMapper sysPostMapper;

    /**
    *  查询表sys_post所有信息
    */
    @Override
    public List<SysPost> selectAllSysPost() { return sysPostMapper.selectAllSysPost();}

            /**
            *  根据主键id查询表sys_post信息
            *  @param id
            */
            @Override
            public SysPost selectSysPostByid(@Param("id") Long id) { return sysPostMapper.selectSysPostByid(id);}

    /**
    *  根据条件查询表sys_post信息
    *  @param sysPost
    */
    @Override
    public List<SysPost> selectSysPostByCondition(SysPost sysPost) { return sysPostMapper.selectSysPostByCondition(sysPost);}

            /**
            *  根据主键id查询表sys_post信息
            *  @param id
            */
            @Override
            public Integer deleteSysPostByid(@Param("id") Long id) { return sysPostMapper.deleteSysPostByid(id);}

            /**
            *  根据主键id更新表sys_post信息
            *  @param sysPost
            */
            @Override
            public Integer updateSysPostByid(SysPost sysPost) { return sysPostMapper.updateSysPostByid(sysPost);}

            /**
            *  新增表sys_post信息
            *  @param sysPost
            */
            @Override
            public Integer insertSysPost(SysPost sysPost) { return sysPostMapper.insertSysPost(sysPost);}
    }
