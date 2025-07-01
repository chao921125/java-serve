package com.cc.server.controller.system;

import com.cc.server.entity.system.SysMenu;
import com.cc.server.service.system.SysMenuService;
import com.cc.server.vo.PageRequest;
import com.cc.server.vo.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
@RequestMapping("/sys-menu")
public class SysMenuController {
    @Resource
    private SysMenuService menuService;

    @Operation(summary = "分页查询菜单")
    @GetMapping("/list")
    public PageResult<SysMenu> list(@RequestParam(defaultValue = "1") int pageNum, 
                                   @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        return menuService.pageSysMenu(pageRequest);
    }

    @Operation(summary = "根据ID查询菜单")
    @GetMapping("/{id}")
    public SysMenu getById(@PathVariable Long id) {
        return menuService.selectSysMenuById(id);
    }

    @Operation(summary = "新增菜单")
    @PostMapping("/add")
    public String add(@RequestBody SysMenu menu) {
        menuService.insertSysMenu(menu);
        return "success";
    }

    @Operation(summary = "修改菜单")
    @PostMapping("/update")
    public String update(@RequestBody SysMenu menu) {
        menuService.updateSysMenuById(menu);
        return "success";
    }

    @Operation(summary = "删除菜单")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        menuService.deleteSysMenuById(id);
        return "success";
    }
}
