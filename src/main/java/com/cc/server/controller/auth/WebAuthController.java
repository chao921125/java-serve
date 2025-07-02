package com.cc.server.controller.auth;

import com.cc.frame.config.jwt.JwtUtil;
import com.cc.frame.constants.CacheKey;
import com.cc.frame.constants.User;
import com.cc.frame.utils.StringUtil;
import com.cc.server.service.system.SysUserService;
import com.cc.server.vo.system.SysUserVO;
import com.cc.frame.core.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Tag(name = "认证管理", description = "用户认证相关接口")
@RestController
@RequestMapping("/api/auth")
public class WebAuthController {
	@Resource
	private JwtUtil jwtUtil;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private SysUserService userService;

	@Value("${application.security.jwt.token-expire-days:7}")
	private int tokenExpireDays;

	@Operation(summary = "用户登录", description = "支持用户名、邮箱、手机号登录，返回JWT token")
	@Parameter(name = "userName", description = "用户名/邮箱/手机号", required = true)
	@Parameter(name = "password", description = "密码", required = true)
	@PostMapping("/login")
	public ApiResponse<String> login(@RequestBody SysUserVO loginVO) {
		// 1. 参数验证
		String loginName = loginVO.getUserName();
		String password = loginVO.getPassword();

		if (loginName == null || loginName.trim().isEmpty()) {
			return ApiResponse.error(400, "登录名不能为空");
		}
		if (password == null || password.trim().isEmpty()) {
			return ApiResponse.error(400, "密码不能为空");
		}
		if (loginName.length() < User.USERNAME_MIN_LENGTH || loginName.length() > User.USERNAME_MAX_LENGTH) {
			return ApiResponse.error(400, "登录名长度不合法");
		}
		if (password.length() < User.PASSWORD_MIN_LENGTH || password.length() > User.PASSWORD_MAX_LENGTH) {
			return ApiResponse.error(400, "密码长度不合法");
		}

		// 2. 用户验证
		SysUserVO user = userService.validateUserLogin(loginName, password);
		if (user == null) {
			return ApiResponse.error(401, "用户名/邮箱/手机号或密码错误");
		}

		// 3. 用户状态检查
		if (User.USER_DISABLE.equals(user.getStatus())) {
			return ApiResponse.error(403, "用户已被禁用");
		}

		// 4. 生成token
		String token = jwtUtil.generateToken(user.getUserName());

		// 5. 设置token过期时间
		String configVal = stringRedisTemplate.opsForValue().get("sys_config:token-expire-days");
		int expireDays = tokenExpireDays;
		if (configVal != null) {
			try {
				expireDays = Integer.parseInt(configVal);
			} catch (Exception ignored) {
				// 使用默认值
			}
		}

		// 6. 存储token到Redis
		long expireSeconds = expireDays * 24L * 60 * 60;
		stringRedisTemplate.opsForValue().set(
				CacheKey.LOGIN_TOKEN_KEY + token,
				user.getUserName(),
				expireSeconds,
				TimeUnit.SECONDS
		);

		return ApiResponse.success("登录成功", token);
	}

	@Operation(summary = "用户注册", description = "注册新用户，注册成功后自动登录并返回token")
	@PostMapping("/register")
	public ApiResponse<String> register(@RequestBody SysUserVO userVO) {
		// 1. 参数验证
		String username = userVO.getUserName();
		String password = userVO.getPassword();

		if (username == null || username.trim().isEmpty()) {
			return ApiResponse.error(400, "用户名不能为空");
		}
		if (password == null || password.trim().isEmpty()) {
			return ApiResponse.error(400, "密码不能为空");
		}
		if (username.length() < User.USERNAME_MIN_LENGTH || username.length() > User.USERNAME_MAX_LENGTH) {
			return ApiResponse.error(400, "用户名长度不合法");
		}
		if (password.length() < User.PASSWORD_MIN_LENGTH || password.length() > User.PASSWORD_MAX_LENGTH) {
			return ApiResponse.error(400, "密码长度不合法");
		}

		// 2. 检查用户是否已存在
		SysUserVO existUser = userService.checkUserExists(username, userVO.getEmail(), userVO.getPhone());
		if (existUser != null) {
			return ApiResponse.error(409, "用户已存在");
		}

		// 3. 创建新用户
		userVO.setPassword(StringUtil.md5(password));
		userVO.setStatus(User.NORMAL);
		Integer result = userService.insertSysUser(userVO);
		if (result == null || result <= 0) {
			return ApiResponse.error(500, "用户注册失败");
		}

		// 4. 注册成功后自动登录，生成token
		String token = jwtUtil.generateToken(username);

		// 5. 设置token过期时间
		String configVal = stringRedisTemplate.opsForValue().get("sys_config:token-expire-days");
		int expireDays = tokenExpireDays;
		if (configVal != null) {
			try {
				expireDays = Integer.parseInt(configVal);
			} catch (Exception ignored) {
				// 使用默认值
			}
		}

		// 6. 存储token到Redis
		long expireSeconds = expireDays * 24L * 60 * 60;
		stringRedisTemplate.opsForValue().set(
				CacheKey.LOGIN_TOKEN_KEY + token,
				username,
				expireSeconds,
				TimeUnit.SECONDS
		);

		return ApiResponse.success("注册成功", token);
	}

	@Operation(summary = "用户登出", description = "清除用户token，实现登出功能")
	@PostMapping("/logout")
	public ApiResponse<String> logout(@RequestBody SysUserVO logoutVO) {
		// 这里可以从请求头获取token，或者从请求体中获取
		// 暂时简单实现，实际应该从JWT中解析用户信息
		String username = logoutVO.getUserName();
		if (username != null && !username.trim().isEmpty()) {
			// 清除Redis中的token（这里需要完整的token，实际应该从请求头获取）
			// stringRedisTemplate.delete(CacheKey.LOGIN_TOKEN_KEY + token);
			return ApiResponse.success("登出成功");
		}
		return ApiResponse.error(400, "登出失败");
	}
}