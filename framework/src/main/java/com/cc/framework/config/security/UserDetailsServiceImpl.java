package com.cc.framework.config.security;

import com.cc.core.entity.SysMenu;
import com.cc.core.entity.SysRole;
import com.cc.core.entity.SysUser;
import com.cc.core.mapper.SysMenuMapper;
import com.cc.core.mapper.SysRoleMapper;
import com.cc.core.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spring Security 用户详情加载服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper userMapper;
    private final SysRoleMapper roleMapper;
    private final SysMenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询用户
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            log.warn("用户不存在: {}", username);
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        if (user.getStatus() != 0) {
            log.warn("用户已被禁用: {}", username);
            throw new UsernameNotFoundException("用户已被禁用: " + username);
        }

        // 2. 查询角色
        List<SysRole> roles = roleMapper.selectRolesByUserId(user.getId());
        Set<String> roleCodes = roles.stream()
                .filter(r -> r.getStatus() == 0)
                .map(SysRole::getCode)
                .collect(Collectors.toSet());

        // 3. 查询权限标识
        List<SysMenu> menus = menuMapper.selectMenusByUserId(user.getId());
        Set<String> permissions = menus.stream()
                .filter(m -> m.getAuth() != null && !m.getAuth().isEmpty())
                .map(SysMenu::getAuth)
                .collect(Collectors.toSet());

        // 4. 构建 LoginUser
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUserName());
        loginUser.setPassword(user.getPassword());
        loginUser.setNickName(user.getNickName());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setPermissions(permissions);
        loginUser.setRoles(roleCodes);
        loginUser.setEnabled(user.getStatus() == 0);

        log.debug("加载用户成功: {}, 角色: {}, 权限: {}", username, roleCodes, permissions);
        return loginUser;
    }
}
