package com.cc.framework.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * Security 工具类
 * 从 SecurityContext 中获取当前登录用户信息
 */
public final class SecurityUtil {

    private SecurityUtil() {}

    /**
     * 获取当前登录用户
     */
    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof LoginUser) {
            return (LoginUser) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 获取当前用户 ID
     */
    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getUserId() : null;
    }

    /**
     * 获取当前用户名
     */
    public static String getUsername() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getUsername() : "anonymous";
    }

    /**
     * 获取当前用户部门 ID
     */
    public static Long getDeptId() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getDeptId() : null;
    }

    /**
     * 获取当前用户昵称
     */
    public static String getNickName() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getNickName() : null;
    }

    /**
     * 获取当前用户权限集合
     */
    public static Set<String> getPermissions() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getPermissions() : Set.of();
    }

    /**
     * 获取当前用户角色集合
     */
    public static Set<String> getRoles() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getRoles() : Set.of();
    }

    /**
     * 是否已认证
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof LoginUser;
    }

    /**
     * 判断是否有指定权限
     */
    public static boolean hasPerm(String permission) {
        Set<String> permissions = getPermissions();
        return permissions.contains(permission) || permissions.contains("*:*:*");
    }

    /**
     * 判断是否有指定角色
     */
    public static boolean hasRole(String role) {
        Set<String> roles = getRoles();
        return roles.contains(role) || roles.contains("admin");
    }
}
