package service.impl;

import entity.SysUserPost;
import java.util.List;
import mapper.SysUserPostMapper;
    import service.SysUserPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * 用户岗位 用户1-N岗位 服务实现类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
@Service
    public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostService {
    @Resource
    private SysUserPostMapper sysUserPostMapper;

    /**
    *  查询表sys_user_post所有信息
    */
    @Override
    public List<SysUserPost> selectAllSysUserPost() { return sysUserPostMapper.selectAllSysUserPost();}

            /**
            *  根据主键id查询表sys_user_post信息
            *  @param id
            */
            @Override
            public SysUserPost selectSysUserPostByid(@Param("id") Long id) { return sysUserPostMapper.selectSysUserPostByid(id);}

    /**
    *  根据条件查询表sys_user_post信息
    *  @param sysUserPost
    */
    @Override
    public List<SysUserPost> selectSysUserPostByCondition(SysUserPost sysUserPost) { return sysUserPostMapper.selectSysUserPostByCondition(sysUserPost);}

            /**
            *  根据主键id查询表sys_user_post信息
            *  @param id
            */
            @Override
            public Integer deleteSysUserPostByid(@Param("id") Long id) { return sysUserPostMapper.deleteSysUserPostByid(id);}

            /**
            *  根据主键id更新表sys_user_post信息
            *  @param sysUserPost
            */
            @Override
            public Integer updateSysUserPostByid(SysUserPost sysUserPost) { return sysUserPostMapper.updateSysUserPostByid(sysUserPost);}

            /**
            *  新增表sys_user_post信息
            *  @param sysUserPost
            */
            @Override
            public Integer insertSysUserPost(SysUserPost sysUserPost) { return sysUserPostMapper.insertSysUserPost(sysUserPost);}
    }
