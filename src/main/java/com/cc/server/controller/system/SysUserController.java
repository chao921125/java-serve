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

	@Operation(summary = "新增用户", description = "新增用户，密码加密存储")
	@PostMapping("/add")
	public ApiResponse<String> addUser(@RequestBody SysUserVO userVO) {
		// 参数校验交由 Service 层处理，Controller 只做参数转发
		Integer result = userService.createUser(userVO);
		if (result == null || result <= 0) {
			return ApiResponse.error(500, "新增用户失败");
		}
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
}
