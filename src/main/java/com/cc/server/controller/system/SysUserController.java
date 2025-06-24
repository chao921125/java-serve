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

	@Operation(summary = "获取用户信息", description = "用户名、邮箱、手机号，密码登录")
	@PreAuthorize("hasAuthority('sys:user:query')")
	@GetMapping("/user")
	public SysUserVO getUserByNameEmailPhone(@RequestParam String username) {
		SysUserVO sysUserVO = new SysUserVO();
		sysUserVO.setUserName(username);
		return userService.getUserByNameEmailPhone(sysUserVO);
	}

	@Operation(summary = "用户登录", description = "用户名、邮箱、手机号，密码登录")
	@Parameter(name = "loginName", description = "用户名/邮箱/手机号", required = true)
	@Parameter(name = "password", description = "密码", required = true)
	@ApiResponse(responseCode = "200", description = "登录成功")
	@PostMapping("/login")
	public SysUserVO login(@RequestParam String loginName, @RequestParam String password) {
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
		sysUserVO.setPassword(password);
		SysUserVO user = userService.getUserByNameEmailPhone(sysUserVO);
		if (user == null) {
			throw new ServiceException("用户名/邮箱/手机号或密码错误", 401);
		}
		if (User.USER_DISABLE.equals(user.getStatus())) {
			throw new ServiceException("用户已被禁用", 403);
		}
		return user;
	}
}
