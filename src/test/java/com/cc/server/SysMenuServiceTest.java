package com.cc.server;

import com.cc.server.entity.system.SysMenu;
import com.cc.server.service.system.SysMenuService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class SysMenuServiceTest {
    @Autowired
    private SysMenuService sysMenuService;

    @Test
    void testInsertAndSelectMenu() {
        SysMenu menu = new SysMenu();
        menu.setName("testmenu");
        Integer result = sysMenuService.insertSysMenu(menu);
        Assertions.assertTrue(result > 0);
        List<SysMenu> menus = sysMenuService.selectSysMenuByCondition(menu);
        Assertions.assertFalse(menus.isEmpty());
    }

    @Test
    void testUpdateMenu() {
        SysMenu menu = new SysMenu();
        menu.setName("updatemenu");
        sysMenuService.insertSysMenu(menu);
        menu.setName("updatedmenu");
        Integer result = sysMenuService.updateSysMenuById(menu);
        Assertions.assertTrue(result >= 0);
    }

    @Test
    void testDeleteMenu() {
        SysMenu menu = new SysMenu();
        menu.setName("deletemenu");
        sysMenuService.insertSysMenu(menu);
        List<SysMenu> menus = sysMenuService.selectSysMenuByCondition(menu);
        if (!menus.isEmpty()) {
            Long id = menus.get(0).getId();
            Integer result = sysMenuService.deleteSysMenuById(id);
            Assertions.assertTrue(result >= 0);
        }
    }
} 