package com.cc.server.controller.auth;

import com.cc.frame.config.jwt.JwtUtil;
import com.cc.frame.constants.CacheKey;
import com.cc.frame.constants.User;
import com.cc.frame.exception.ServiceException;
import com.cc.frame.utils.StringUtil;
import com.cc.server.service.system.SysUserService;
import com.cc.server.vo.system.SysUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Tag(name = "系统", description = "用户")
@RestController
@RequestMapping("/api/auth")
public class WebAuth {
	@Resource
	private JwtUtil jwtUtil;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private SysUserService userService;

	@Value("${application.security.jwt.token-expire-days:7}")
	private int tokenExpireDays;

	@Operation(summary = "用户登录", description = "用户名、邮箱、手机号，密码登录，返回token")
	@Parameter(name = "loginName", description = "用户名/邮箱/手机号", required = true)
	@Parameter(name = "password", description = "密码", required = true)
	@ApiResponse(responseCode = "200", description = "登录成功")
	@PostMapping("/login")
	public String login(@RequestBody SysUserVO loginVO) {
		String loginName = loginVO.getUserName();
		String password = loginVO.getPassword();
		if (loginName == null || loginName.length() < User.USERNAME_MIN_LENGTH || loginName.length() > User.USERNAME_MAX_LENGTH) {
			throw new ServiceException("登录名长度不合法", 400);
		}
		if (password == null || password.length() < User.PASSWORD_MIN_LENGTH || password.length() > User.PASSWORD_MAX_LENGTH) {
			throw new ServiceException("密码长度不合法", 400);
		}
		SysUserVO sysUserVO = new SysUserVO();
		sysUserVO.setUserName(loginName);
		sysUserVO.setEmail(loginName);
		sysUserVO.setPhone(loginName);
		sysUserVO.setPassword(StringUtil.md5(password));
		SysUserVO user = userService.getUserByNameEmailPhone(sysUserVO);
		if (user == null) {
			throw new ServiceException("用户名/邮箱/手机号或密码错误", 401);
		}
		if (User.USER_DISABLE.equals(user.getStatus())) {
			throw new ServiceException("用户已被禁用", 403);
		}
		// 优先从系统配置读取token有效期
		String configVal = stringRedisTemplate.opsForValue().get("sys_config:token-expire-days");
		int expireDays = tokenExpireDays;
		if (configVal != null) {
			try {
				expireDays = Integer.parseInt(configVal);
			} catch (Exception ignored) {
			}
		}
		String token = jwtUtil.generateToken(user.getUserName());
		long expireSeconds = expireDays * 24L * 60 * 60;
		stringRedisTemplate.opsForValue().set(CacheKey.LOGIN_TOKEN_KEY + token, user.getUserName(), expireSeconds, TimeUnit.SECONDS);
		return token;
	}

	@Operation(summary = "用户注册", description = "注册新用户，注册成功后自动登录并返回token")
	@PostMapping("/register")
	public String register(@RequestBody SysUserVO userVO) {
		String username = userVO.getUserName();
		String password = userVO.getPassword();
		if (username == null || username.length() < User.USERNAME_MIN_LENGTH || username.length() > User.USERNAME_MAX_LENGTH) {
			throw new ServiceException("用户名长度不合法", 400);
		}
		if (password == null || password.length() < User.PASSWORD_MIN_LENGTH || password.length() > User.PASSWORD_MAX_LENGTH) {
			throw new ServiceException("密码长度不合法", 400);
		}
		// 检查用户名/邮箱/手机号是否已存在
		SysUserVO checkVO = new SysUserVO();
		checkVO.setUserName(username);
		checkVO.setEmail(userVO.getEmail());
		checkVO.setPhone(userVO.getPhone());
		SysUserVO exist = userService.getUserByNameEmailPhone(checkVO);
		if (exist != null) {
			throw new ServiceException("用户已存在", 409);
		}
		// 密码加密
		userVO.setPassword(StringUtil.md5(password));
		userVO.setStatus(User.NORMAL);
		userService.insertSysUser(userVO);
		// 注册成功后自动登录，生成token
		// 优先从系统配置读取token有效期
		String configVal = stringRedisTemplate.opsForValue().get("sys_config:token-expire-days");
		int expireDays = tokenExpireDays;
		if (configVal != null) {
			try {
				expireDays = Integer.parseInt(configVal);
			} catch (Exception ignored) {
			}
		}
		String token = jwtUtil.generateToken(username);
		long expireSeconds = expireDays * 24L * 60 * 60;
		stringRedisTemplate.opsForValue().set(CacheKey.LOGIN_TOKEN_KEY + token, username, expireSeconds, java.util.concurrent.TimeUnit.SECONDS);
		return token;
	}
}
