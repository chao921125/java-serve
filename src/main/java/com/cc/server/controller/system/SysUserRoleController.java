package com.cc.server.controller.system;

import com.cc.server.entity.system.SysUserRole;
import com.cc.server.service.system.SysUserRoleService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 用户角色 用户N-1角色 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "用户角色管理", description = "用户角色管理接口")
@RestController
@RequestMapping("/api-admin/sys-user-role")
public class SysUserRoleController extends BaseController<SysUserRole, SysUserRoleService> {
    @Resource
    private SysUserRoleService userRoleService;

    @Override
    protected SysUserRoleService getService() {
        return userRoleService;
    }

    @Override
    protected SysUserRole doGetById(Long id) {
        return userRoleService.selectSysUserRoleById(id);
    }

    @Override
    protected boolean doAdd(SysUserRole entity) {
        return userRoleService.insertSysUserRole(entity) > 0;
    }

    @Override
    protected boolean doDelete(Long id) {
        return userRoleService.deleteSysUserRoleById(id) > 0;
    }

    @Override
    protected boolean doUpdate(SysUserRole entity) {
        return userRoleService.updateSysUserRoleById(entity) > 0;
    }

    @Override
    protected List<SysUserRole> doList() {
        return userRoleService.selectAllSysUserRole();
    }

    @Override
    protected PageResult<SysUserRole> doPage(PageRequest pageRequest) {
        return userRoleService.pageSysUserRole(pageRequest);
    }
}
