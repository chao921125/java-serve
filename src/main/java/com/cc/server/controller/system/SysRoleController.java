package com.cc.server.controller.system;

import com.cc.server.entity.system.SysRole;
import com.cc.server.service.system.SysRoleService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
public class SysRoleController extends BaseController<SysRole, SysRoleService> {
	@Resource
	private SysRoleService roleService;

	@Override
	protected SysRoleService getService() {
		return roleService;
	}

	@Override
	protected SysRole doGetById(Long id) {
		return roleService.selectSysRoleById(id);
	}

	@Override
	protected boolean doAdd(SysRole entity) {
		return roleService.insertSysRole(entity) > 0;
	}

	@Override
	protected boolean doDelete(Long id) {
		return roleService.deleteSysRoleById(id) > 0;
	}

	@Override
	protected boolean doUpdate(SysRole entity) {
		return roleService.updateSysRoleById(entity) > 0;
	}

	@Override
	protected List<SysRole> doList() {
		return roleService.selectAllSysRole();
	}

	@Override
	protected PageResult<SysRole> doPage(PageRequest pageRequest) {
		return roleService.pageSysRole(pageRequest);
	}
}
