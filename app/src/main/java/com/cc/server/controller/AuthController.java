package com.cc.server.controller;

import com.cc.core.dto.ChangePasswordDTO;
import com.cc.core.dto.LoginDTO;
import com.cc.core.dto.RegisterDTO;
import com.cc.core.entity.LogLogin;
import com.cc.core.entity.SysMenu;
import com.cc.core.entity.SysRole;
import com.cc.core.entity.SysUser;
import com.cc.core.mapper.SysMenuMapper;
import com.cc.core.mapper.SysRoleMapper;
import com.cc.core.service.SysUserService;
import com.cc.core.vo.LoginVO;
import com.cc.core.vo.UserInfoVO;
import com.cc.framework.base.R;
import com.cc.framework.config.security.JwtUtil;
import com.cc.framework.config.security.LoginUser;
import com.cc.framework.config.security.SecurityUtil;
import com.cc.framework.exception.ServiceException;
import com.cc.framework.utils.IpUtil;
import com.cc.framework.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SysUserService userService;
    private final SysMenuMapper menuMapper;
    private final SysRoleMapper roleMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final com.cc.core.mapper.LogLoginMapper logLoginMapper;

    /**
     * 登录
     */
    @PostMapping("/login")
    public R<LoginVO> login(@Valid @RequestBody LoginDTO dto, HttpServletRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );
        } catch (Exception e) {
            log.warn("登录失败: username={}, error={}", dto.getUsername(), e.getMessage());
            recordLoginLog(dto.getUsername(), 1, "登录失败: " + e.getMessage(), request);
            throw ServiceException.badRequest("用户名或密码错误");
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(loginUser.getUserId(), loginUser.getUsername(), null);
        String refreshToken = jwtUtil.generateRefreshToken(loginUser.getUserId(), loginUser.getUsername());

        // 更新登录信息
        SysUser update = new SysUser();
        update.setId(loginUser.getUserId());
        update.setLoginIp(IpUtil.getClientIp(request));
        update.setLoginTime(LocalDateTime.now().toString());
        userService.updateById(update);

        recordLoginLog(dto.getUsername(), 0, "登录成功", request);

        LoginVO vo = LoginVO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .expireIn(86400L)
                .userInfo(buildUserInfo(loginUser))
                .build();

        return R.ok(vo);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public R<Void> register(@Valid @RequestBody RegisterDTO dto) {
        if (userService.getByUsername(dto.getUsername()) != null) {
            throw ServiceException.badRequest("用户名已存在");
        }
        SysUser user = new SysUser();
        user.setUserName(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setNickName(dto.getNickName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setStatus(0);
        userService.addUser(user, Collections.emptyList(), null);
        return R.ok();
    }

    /**
     * 刷新 Token
     */
    @PostMapping("/refresh")
    public R<LoginVO> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw ServiceException.badRequest("刷新令牌不能为空");
        }
        String token = refreshToken.substring(7);
        if (!jwtUtil.isTokenValid(token)) {
            throw ServiceException.badRequest("刷新令牌已失效，请重新登录");
        }
        String username = jwtUtil.extractUsername(token);
        Long userId = jwtUtil.extractUserId(token);
        String newToken = jwtUtil.generateToken(userId, username, null);
        String newRefresh = jwtUtil.generateRefreshToken(userId, username);

        LoginVO vo = LoginVO.builder()
                .token(newToken)
                .refreshToken(newRefresh)
                .expireIn(86400L)
                .build();
        return R.ok(vo);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public R<UserInfoVO> info() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        if (loginUser == null) {
            throw ServiceException.badRequest("未登录");
        }
        return R.ok(buildUserInfo(loginUser));
    }

    /**
     * 获取当前用户菜单
     */
    @GetMapping("/menus")
    public R<List<SysMenu>> menus() {
        Long userId = SecurityUtil.getUserId();
        List<SysMenu> menus = menuMapper.selectMenusByUserId(userId);
        return R.ok(menus);
    }

    /**
     * 修改密码
     */
    @PostMapping("/changePassword")
    public R<Void> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        Long userId = SecurityUtil.getUserId();
        if (userId == null) {
            throw ServiceException.badRequest("未登录");
        }
        userService.changePassword(userId, dto.getOldPassword(), dto.getNewPassword());
        return R.ok();
    }

    /**
     * 登出（客户端清除 Token 即可，无状态无需服务端处理）
     */
    @PostMapping("/logout")
    public R<Void> logout() {
        log.info("用户登出: {}", SecurityUtil.getUsername());
        return R.ok();
    }

    // ---- private ----

    private UserInfoVO buildUserInfo(LoginUser loginUser) {
        return UserInfoVO.builder()
                .userId(loginUser.getUserId())
                .userName(loginUser.getUsername())
                .nickName(loginUser.getNickName())
                .deptId(loginUser.getDeptId())
                .roles(loginUser.getRoles())
                .permissions(loginUser.getPermissions())
                .build();
    }

    private void recordLoginLog(String username, int status, String message, HttpServletRequest request) {
        try {
            LogLogin logLogin = new LogLogin();
            logLogin.setUserName(username);
            logLogin.setIp(IpUtil.getClientIp(request));
            logLogin.setLoginTime(LocalDateTime.now());
            logLogin.setStatus(status);
            logLogin.setMessage(message);
            logLogin.setSystem(request.getHeader("User-Agent"));
            logLoginMapper.insert(logLogin);
        } catch (Exception e) {
            log.error("记录登录日志失败", e);
        }
    }
}
