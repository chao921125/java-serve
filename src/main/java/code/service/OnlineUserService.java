package service;

import entity.OnlineUser;
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
    public interface OnlineUserService extends IService<OnlineUser> {
    /**
    *  查询表online_user所有信息
    */
    List<OnlineUser> selectAllOnlineUser();

            /**
            *  根据主键id查询表online_user信息
            *  @param id
            */
            OnlineUser selectOnlineUserByid(@Param("id") Long id);

    /**
    *  根据条件查询表online_user信息
    *  @param onlineUser
    */
    List<OnlineUser> selectOnlineUserByCondition(OnlineUser onlineUser);

            /**
            *  根据主键id查询表online_user信息
            *  @param id
            */
            Integer deleteOnlineUserByid(@Param("id") Long id);

            /**
            *  根据主键id更新表online_user信息
            *  @param onlineUser
            */
            Integer updateOnlineUserByid(OnlineUser onlineUser);

            /**
            *  新增表online_user信息
            *  @param onlineUser
            */
            Integer insertOnlineUser(OnlineUser onlineUser);
    }
