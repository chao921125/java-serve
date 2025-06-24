package mapper;

import entity.SysUserPost;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* <p>
    * 用户岗位 用户1-N岗位 Mapper 接口
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysUserPostMapper extends BaseMapper<SysUserPost> {
    /**
    *  查询表sys_user_post所有信息
    */
    List<SysUserPost> selectAllSysUserPost();

            /**
            *  根据主键id查询表sys_user_post信息
            *  @param id
            */
            SysUserPost selectSysUserPostByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_user_post信息
    *  @param sysUserPost
    */
    List<SysUserPost> selectSysUserPostByCondition(SysUserPost sysUserPost);

            /**
            *  根据主键id查询表sys_user_post信息
            *  @param id
            */
            Integer deleteSysUserPostByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_user_post信息
            *  @param sysUserPost
            */
            Integer updateSysUserPostByid(SysUserPost sysUserPost);

            /**
            *  新增表sys_user_post信息
            *  @param sysUserPost
            */
            Integer insertSysUserPost(SysUserPost sysUserPost);

    }
