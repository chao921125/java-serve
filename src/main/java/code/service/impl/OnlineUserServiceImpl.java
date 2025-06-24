package service.impl;

import entity.OnlineUser;
import java.util.List;
import mapper.OnlineUserMapper;
    import service.OnlineUserService;
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
    public class OnlineUserServiceImpl extends ServiceImpl<OnlineUserMapper, OnlineUser> implements OnlineUserService {
    @Resource
    private OnlineUserMapper onlineUserMapper;

    /**
    *  查询表online_user所有信息
    */
    @Override
    public List<OnlineUser> selectAllOnlineUser() { return onlineUserMapper.selectAllOnlineUser();}

            /**
            *  根据主键id查询表online_user信息
            *  @param id
            */
            @Override
            public OnlineUser selectOnlineUserByid(@Param("id") Long id) { return onlineUserMapper.selectOnlineUserByid(id);}

    /**
    *  根据条件查询表online_user信息
    *  @param onlineUser
    */
    @Override
    public List<OnlineUser> selectOnlineUserByCondition(OnlineUser onlineUser) { return onlineUserMapper.selectOnlineUserByCondition(onlineUser);}

            /**
            *  根据主键id查询表online_user信息
            *  @param id
            */
            @Override
            public Integer deleteOnlineUserByid(@Param("id") Long id) { return onlineUserMapper.deleteOnlineUserByid(id);}

            /**
            *  根据主键id更新表online_user信息
            *  @param onlineUser
            */
            @Override
            public Integer updateOnlineUserByid(OnlineUser onlineUser) { return onlineUserMapper.updateOnlineUserByid(onlineUser);}

            /**
            *  新增表online_user信息
            *  @param onlineUser
            */
            @Override
            public Integer insertOnlineUser(OnlineUser onlineUser) { return onlineUserMapper.insertOnlineUser(onlineUser);}
    }
