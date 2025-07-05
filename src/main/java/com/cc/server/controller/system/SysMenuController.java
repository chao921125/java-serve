package com.cc.server.controller.system;

import com.cc.server.entity.system.SysMenu;
import com.cc.server.service.system.SysMenuService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.BaseController;
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
@RequestMapping("/api-admin/sys-menu")
public class SysMenuController extends BaseController<SysMenu, SysMenuService> {
    @Resource
    private SysMenuService menuService;

    @Override
    protected SysMenuService getService() {
        return menuService;
    }

    @Override
    protected SysMenu doGetById(Long id) {
        return menuService.selectSysMenuById(id);
    }

    @Override
    protected boolean doAdd(SysMenu entity) {
        return menuService.insertSysMenu(entity) > 0;
    }

    @Override
    protected boolean doDelete(Long id) {
        return menuService.deleteSysMenuById(id) > 0;
    }

    @Override
    protected boolean doUpdate(SysMenu entity) {
        return menuService.updateSysMenuById(entity) > 0;
    }

    @Override
    protected List<SysMenu> doList() {
        return menuService.selectAllSysMenu();
    }

    @Override
    protected PageResult<SysMenu> doPage(PageRequest pageRequest) {
        return menuService.pageSysMenu(pageRequest);
    }
}
