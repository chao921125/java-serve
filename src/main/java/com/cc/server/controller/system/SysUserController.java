package com.cc.server.controller.system;

import com.cc.server.service.system.SysUserService;
import com.cc.server.vo.system.SysUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.cc.frame.constants.User;
import com.cc.frame.exception.ServiceException;
import com.cc.frame.config.jwt.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.cc.frame.constants.CacheKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "系统", description = "用户")
@RestController
@RequestMapping("/admin/sys-user")
public class SysUserController {
	@Resource
	private SysUserService userService;

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Value("${application.security.jwt.token-expire-days:7}")
	private int tokenExpireDays;

	@Operation(summary = "获取用户信息", description = "用户名、邮箱、手机号，密码登录")
	@PreAuthorize("hasAuthority('sys:user:query')")
	@GetMapping("/user")
	public SysUserVO getUserByNameEmailPhone(@RequestParam String username) {
		SysUserVO sysUserVO = new SysUserVO();
		sysUserVO.setUserName(username);
		return userService.getUserByNameEmailPhone(sysUserVO);
	}

	@Operation(summary = "新增用户", description = "新增用户，密码MD5加密")
	@PostMapping("/add")
	public String addUser(@RequestBody SysUserVO userVO) {
		userVO.setPassword(md5(userVO.getPassword()));
		userService.insertSysUser(userVO);
		return "success";
	}

	@Operation(summary = "逻辑删除用户", description = "逻辑删除用户，status=9")
	@PostMapping("/logic-delete/{id}")
	public String logicDeleteUser(@PathVariable Long id) {
		SysUserVO vo = userService.selectSysUserById(id);
		if (vo == null) return "not found";
		vo.setStatus("9");
		userService.updateSysUserById(vo);
		return "success";
	}

	@Operation(summary = "物理删除用户", description = "物理删除用户")
	@PostMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteSysUserById(id);
		return "success";
	}

	@Operation(summary = "修改用户信息", description = "修改用户信息")
	@PostMapping("/update")
	public String updateUser(@RequestBody SysUserVO userVO) {
		userService.updateSysUserById(userVO);
		return "success";
	}

	@Operation(summary = "用户登录", description = "用户名、邮箱、手机号，密码登录，返回token")
	@Parameter(name = "loginName", description = "用户名/邮箱/手机号", required = true)
	@Parameter(name = "password", description = "密码", required = true)
	@ApiResponse(responseCode = "200", description = "登录成功")
	@PostMapping("/login")
	public String login(@RequestParam String loginName, @RequestParam String password) {
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
		sysUserVO.setPassword(md5(password));
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
			try { expireDays = Integer.parseInt(configVal); } catch (Exception ignored) {}
		}
		String token = jwtUtil.generateToken(user.getUserName());
		long expireSeconds = expireDays * 24L * 60 * 60;
		stringRedisTemplate.opsForValue().set(CacheKey.LOGIN_TOKEN_KEY + token, user.getUserName(), expireSeconds, TimeUnit.SECONDS);
		return token;
	}

	// MD5加密工具
	private String md5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
