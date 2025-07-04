package com.cc.server.controller.system;

import com.cc.server.entity.system.SysMenu;
import com.cc.server.service.system.SysMenuService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "菜单管理", description = "菜单管理接口")
@RestController
@RequestMapping("/api-admin/sys-menu")
public class SysMenuController {
    @Resource
    private SysMenuService menuService;

    @Operation(summary = "分页查询菜单")
    @GetMapping("/list")
    public ApiResponse<PageResult<SysMenu>> list(@RequestParam(defaultValue = "1") int pageNum, 
                                   @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        return ApiResponse.success(menuService.pageSysMenu(pageRequest));
    }

    @Operation(summary = "根据ID查询菜单")
    @GetMapping("/{id}")
    public ApiResponse<SysMenu> getById(@PathVariable Long id) {
        SysMenu result = menuService.selectSysMenuById(id);
        if (result == null) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }

    @Operation(summary = "新增菜单")
    @PostMapping("/add")
    public ApiResponse<String> add(@RequestBody SysMenu menu) {
        menuService.insertSysMenu(menu);
        return ApiResponse.success("新增成功", null);
    }

    @Operation(summary = "修改菜单")
    @PostMapping("/update")
    public ApiResponse<String> update(@RequestBody SysMenu menu) {
        menuService.updateSysMenuById(menu);
        return ApiResponse.success("修改成功", null);
    }

    @Operation(summary = "删除菜单")
    @PostMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        menuService.deleteSysMenuById(id);
        return ApiResponse.success("删除成功", null);
    }
}
