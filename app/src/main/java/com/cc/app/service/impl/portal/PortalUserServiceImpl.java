package com.cc.app.service.impl.portal;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.portal.PortalUser;
import com.cc.core.mapper.portal.PortalUserMapper;
import com.cc.core.service.portal.PortalUserService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * PortalUser 服务实现
 */
@Service
public class PortalUserServiceImpl extends ServiceImpl<PortalUserMapper, PortalUser> implements PortalUserService {

    // ==== Business Logic Methods ====

    @Override
    public PortalUser login(String username, String password) {
        PortalUser user = getOne(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PortalUser>()
                .eq(PortalUser::getUsername, username)
        );
        if (user == null) throw new RuntimeException("用户名或密码错误");
        if (user.getStatus() != 1) throw new RuntimeException("账户已停用");
        // BCrypt 密码校验
        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder =
            new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        user.setLastLoginTime(java.time.LocalDateTime.now().toString());
        updateById(user);
        return user;
    }

    @Override
    public boolean isActive(Long id) {
        PortalUser user = getById(id);
        return user != null && user.getStatus() == 1;
    }

}
