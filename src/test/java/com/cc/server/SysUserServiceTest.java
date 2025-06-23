package com.cc.server;

import com.cc.server.entity.system.SysUser;
import com.cc.server.service.system.SysUserService;
import com.cc.server.vo.system.SysUserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class SysUserServiceTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    void testInsertAndSelectUser() {
        SysUserVO user = new SysUserVO();
        user.setUserName("testuser");
        user.setPassword("123456");
        Integer result = sysUserService.insertSysUser(user);
        Assertions.assertTrue(result > 0);
        List<SysUserVO> users = sysUserService.selectSysUserByCondition(user);
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    void testUpdateUser() {
        SysUserVO user = new SysUserVO();
        user.setUserName("updateuser");
        user.setPassword("init");
        sysUserService.insertSysUser(user);
        user.setPassword("updated");
        Integer result = sysUserService.updateSysUserById(user);
        Assertions.assertTrue(result >= 0);
    }

    @Test
    void testDeleteUser() {
        SysUserVO user = new SysUserVO();
        user.setUserName("deleteuser");
        user.setPassword("init");
        sysUserService.insertSysUser(user);
        List<SysUserVO> users = sysUserService.selectSysUserByCondition(user);
        if (!users.isEmpty()) {
            Long id = users.get(0).getId();
            Integer result = sysUserService.deleteSysUserById(id);
            Assertions.assertTrue(result >= 0);
        }
    }
} 