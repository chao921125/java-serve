package com.cc.server.controller.system;

import com.cc.server.entity.system.SysRole;
import com.cc.server.service.system.SysRoleService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "角色管理", description = "角色管理接口")
@RestController
@RequestMapping("/api-admin/sys-role")
public class SysRoleController {
	@Resource
	private SysRoleService roleService;

	@Operation(summary = "分页查询角色")
	@GetMapping("/list")
	public ApiResponse<PageResult<SysRole>> list(@RequestParam(defaultValue = "1") int pageNum, 
	                               @RequestParam(defaultValue = "10") int pageSize) {
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageNum(pageNum);
		pageRequest.setPageSize(pageSize);
		return ApiResponse.success(roleService.pageSysRole(pageRequest));
	}

	@Operation(summary = "根据ID查询角色")
	@GetMapping("/{id}")
	public ApiResponse<SysRole> getById(@PathVariable Long id) {
		SysRole result = roleService.selectSysRoleById(id);
		if (result == null) {
			return ApiResponse.success("未查询到数据", null);
		}
		return ApiResponse.success(result);
	}

	@Operation(summary = "新增角色")
	@PostMapping("/add")
	public ApiResponse<String> add(@RequestBody SysRole role) {
		roleService.insertSysRole(role);
		return ApiResponse.success("新增成功", null);
	}

	@Operation(summary = "修改角色")
	@PostMapping("/update")
	public ApiResponse<String> update(@RequestBody SysRole role) {
		roleService.updateSysRoleById(role);
		return ApiResponse.success("修改成功", null);
	}

	@Operation(summary = "删除角色")
	@PostMapping("/delete/{id}")
	public ApiResponse<String> delete(@PathVariable Long id) {
		roleService.deleteSysRoleById(id);
		return ApiResponse.success("删除成功", null);
	}
}
