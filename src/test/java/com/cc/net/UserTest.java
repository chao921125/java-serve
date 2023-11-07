package com.cc.net;

import com.cc.net.entity.system.SysUser;
import com.cc.net.mapper.system.SysUserMapper;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Resource
    SysUserMapper sysUserMapper;

    @Test
    public void testSelectUserList() {
        List<SysUser> users = sysUserMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testCreateUser() {
        SysUser user = new SysUser();
        user.setId(UUID.randomUUID().toString());
        user.setDeptId(1L);
        user.setUserName("Test");
        user.setNickName("Test");
        user.setPassword("123345");
        sysUserMapper.insert(user);
    }
}
