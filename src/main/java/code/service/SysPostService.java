package service;

import entity.SysPost;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
    * 岗位表 服务类
    * </p>
*
* @author cc
* @since 2025-06-23 18:27:36
*/
    public interface SysPostService extends IService<SysPost> {
    /**
    *  查询表sys_post所有信息
    */
    List<SysPost> selectAllSysPost();

            /**
            *  根据主键id查询表sys_post信息
            *  @param id
            */
            SysPost selectSysPostByid(@Param("id") Long id);

    /**
    *  根据条件查询表sys_post信息
    *  @param sysPost
    */
    List<SysPost> selectSysPostByCondition(SysPost sysPost);

            /**
            *  根据主键id查询表sys_post信息
            *  @param id
            */
            Integer deleteSysPostByid(@Param("id") Long id);

            /**
            *  根据主键id更新表sys_post信息
            *  @param sysPost
            */
            Integer updateSysPostByid(SysPost sysPost);

            /**
            *  新增表sys_post信息
            *  @param sysPost
            */
            Integer insertSysPost(SysPost sysPost);
    }
