package com.cc.server;

import com.cc.server.entity.system.SysRole;
import com.cc.server.service.system.SysRoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    private SysRoleService sysRoleService;

    @Test
    void testInsertAndSelectRole() {
        SysRole role = new SysRole();
        role.setName("testrole");
        Integer result = sysRoleService.insertSysRole(role);
        Assertions.assertTrue(result > 0);
        List<SysRole> roles = sysRoleService.selectSysRoleByCondition(role);
        Assertions.assertFalse(roles.isEmpty());
    }

    @Test
    void testUpdateRole() {
        SysRole role = new SysRole();
        role.setName("updaterole");
        sysRoleService.insertSysRole(role);
        role.setName("updatedrole");
        Integer result = sysRoleService.updateSysRoleById(role);
        Assertions.assertTrue(result >= 0);
    }

    @Test
    void testDeleteRole() {
        SysRole role = new SysRole();
        role.setName("deleterole");
        sysRoleService.insertSysRole(role);
        List<SysRole> roles = sysRoleService.selectSysRoleByCondition(role);
        if (!roles.isEmpty()) {
            Long id = roles.get(0).getId();
            Integer result = sysRoleService.deleteSysRoleById(id);
            Assertions.assertTrue(result >= 0);
        }
    }
} 