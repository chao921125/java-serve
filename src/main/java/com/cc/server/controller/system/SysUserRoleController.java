package com.cc.server.controller.system;

import com.cc.server.entity.system.SysUserRole;
import com.cc.server.service.system.SysUserRoleService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
public class SysUserRoleController {
    @Resource
    private SysUserRoleService userRoleService;

    @Operation(summary = "分页查询用户角色")
    @GetMapping("/list")
    public ApiResponse<PageResult<SysUserRole>> list(@RequestParam(defaultValue = "1") int pageNum, 
                                       @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        return ApiResponse.success(userRoleService.pageSysUserRole(pageRequest));
    }

    @Operation(summary = "根据ID查询用户角色")
    @GetMapping("/{id}")
    public ApiResponse<SysUserRole> getById(@PathVariable Long id) {
        SysUserRole result = userRoleService.selectSysUserRoleById(id);
        if (result == null) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }

    @Operation(summary = "新增用户角色")
    @PostMapping("/add")
    public ApiResponse<String> add(@RequestBody SysUserRole userRole) {
        userRoleService.insertSysUserRole(userRole);
        return ApiResponse.success("新增成功", null);
    }

    @Operation(summary = "修改用户角色")
    @PostMapping("/update")
    public ApiResponse<String> update(@RequestBody SysUserRole userRole) {
        userRoleService.updateSysUserRoleById(userRole);
        return ApiResponse.success("修改成功", null);
    }

    @Operation(summary = "删除用户角色")
    @PostMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        userRoleService.deleteSysUserRoleById(id);
        return ApiResponse.success("删除成功", null);
    }
}
