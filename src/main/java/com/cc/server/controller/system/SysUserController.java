package com.cc.server.controller.system;

import com.cc.server.service.system.SysUserService;
import com.cc.server.vo.system.SysUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.utils.StringUtil;
import com.cc.frame.core.ApiResponse;
import com.cc.frame.constants.User;
import com.cc.frame.constants.CacheKey;
import com.cc.frame.config.jwt.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.concurrent.TimeUnit;

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
@RequestMapping("/api-admin/sys-user")
public class SysUserController {
	@Autowired
	private SysUserService userService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	@Value("${token.expire.days:7}")
	private int tokenExpireDays;

	@Operation(summary = "获取用户信息", description = "用户名、邮箱、手机号，密码登录")
	@PreAuthorize("hasAuthority('sys:user:query')")
	@GetMapping("/user")
	public ApiResponse<SysUserVO> getUserByNameEmailPhone(@RequestParam String username) {
		SysUserVO sysUserVO = new SysUserVO();
		sysUserVO.setUserName(username);
		SysUserVO result = userService.getUserByNameEmailPhone(sysUserVO);
		if (result == null) {
			return ApiResponse.success("未查询到数据", null);
		}
		return ApiResponse.success(result);
	}

	@Operation(summary = "新增用户", description = "新增用户，密码MD5加密")
	@PostMapping("/add")
	public ApiResponse<String> addUser(@RequestBody SysUserVO userVO) {
		userVO.setPassword(StringUtil.md5(userVO.getPassword()));
		userService.insertSysUser(userVO);
		return ApiResponse.success("新增成功", null);
	}

	@Operation(summary = "逻辑删除用户", description = "逻辑删除用户，status=9")
	@PostMapping("/logic-delete/{id}")
	public ApiResponse<String> logicDeleteUser(@PathVariable Long id) {
		SysUserVO vo = userService.selectSysUserById(id);
		if (vo == null) return ApiResponse.error(404, "用户不存在");
		vo.setStatus("9");
		userService.updateSysUserById(vo);
		return ApiResponse.success("逻辑删除成功", null);
	}

	@Operation(summary = "物理删除用户", description = "物理删除用户")
	@PostMapping("/delete/{id}")
	public ApiResponse<String> deleteUser(@PathVariable Long id) {
		userService.deleteSysUserById(id);
		return ApiResponse.success("物理删除成功", null);
	}

	@Operation(summary = "修改用户信息", description = "修改用户信息")
	@PostMapping("/update")
	public ApiResponse<String> updateUser(@RequestBody SysUserVO userVO) {
		userService.updateSysUserById(userVO);
		return ApiResponse.success("修改成功", null);
	}

	@Operation(summary = "分页查询用户", description = "分页查询用户")
	@GetMapping("/list")
	public ApiResponse<PageResult<SysUserVO>> list(@RequestParam(defaultValue = "1") int pageNum,
												   @RequestParam(defaultValue = "10") int pageSize) {
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageNum(pageNum);
		pageRequest.setPageSize(pageSize);
		return ApiResponse.success(userService.pageSysUser(pageRequest));
	}

	@PostMapping("/login")
	public ApiResponse<String> login(@RequestBody SysUserVO loginVO) {
		String loginName = loginVO.getUserName();
		String password = loginVO.getPassword();
		if (loginName == null || loginName.length() < User.USERNAME_MIN_LENGTH || loginName.length() > User.USERNAME_MAX_LENGTH) {
			return ApiResponse.error(400, "登录名长度不合法");
		}
		if (password == null || password.length() < User.PASSWORD_MIN_LENGTH || password.length() > User.PASSWORD_MAX_LENGTH) {
			return ApiResponse.error(400, "密码长度不合法");
		}
		SysUserVO sysUserVO = new SysUserVO();
		sysUserVO.setUserName(loginName);
		sysUserVO.setEmail(loginName);
		sysUserVO.setPhone(loginName);
		sysUserVO.setPassword(StringUtil.md5(password));
		SysUserVO user = userService.getUserByNameEmailPhone(sysUserVO);
		if (user == null) {
			return ApiResponse.error(401, "用户名/邮箱/手机号或密码错误");
		}
		if (User.USER_DISABLE.equals(user.getStatus())) {
			return ApiResponse.error(403, "用户已被禁用");
		}
		String configVal = stringRedisTemplate.opsForValue().get("sys_config:token-expire-days");
		int expireDays = tokenExpireDays;
		if (configVal != null) {
			try { expireDays = Integer.parseInt(configVal); } catch (Exception ignored) {}
		}
		String token = jwtUtil.generateToken(user.getUserName());
		long expireSeconds = expireDays * 24L * 60 * 60;
		stringRedisTemplate.opsForValue().set(CacheKey.LOGIN_TOKEN_KEY + token, user.getUserName(), expireSeconds, TimeUnit.SECONDS);
		return ApiResponse.success(token);
	}

	@PostMapping("/register")
	public ApiResponse<String> register(@RequestBody SysUserVO userVO) {
		String username = userVO.getUserName();
		String password = userVO.getPassword();
		if (username == null || username.length() < User.USERNAME_MIN_LENGTH || username.length() > User.USERNAME_MAX_LENGTH) {
			return ApiResponse.error(400, "用户名长度不合法");
		}
		if (password == null || password.length() < User.PASSWORD_MIN_LENGTH || password.length() > User.PASSWORD_MAX_LENGTH) {
			return ApiResponse.error(400, "密码长度不合法");
		}
		SysUserVO checkVO = new SysUserVO();
		checkVO.setUserName(username);
		checkVO.setEmail(userVO.getEmail());
		checkVO.setPhone(userVO.getPhone());
		SysUserVO exist = userService.getUserByNameEmailPhone(checkVO);
		if (exist != null) {
			return ApiResponse.error(409, "用户已存在");
		}
		userVO.setPassword(StringUtil.md5(password));
		userVO.setStatus(User.NORMAL);
		userService.insertSysUser(userVO);
		String configVal = stringRedisTemplate.opsForValue().get("sys_config:token-expire-days");
		int expireDays = tokenExpireDays;
		if (configVal != null) {
			try { expireDays = Integer.parseInt(configVal); } catch (Exception ignored) {}
		}
		String token = jwtUtil.generateToken(username);
		long expireSeconds = expireDays * 24L * 60 * 60;
		stringRedisTemplate.opsForValue().set(CacheKey.LOGIN_TOKEN_KEY + token, username, expireSeconds, java.util.concurrent.TimeUnit.SECONDS);
		return ApiResponse.success(token);
	}
}
