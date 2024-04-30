package com.cc.serve;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.serve.entity.SysUser;
import com.cc.serve.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DataTest {
    private static final Logger logger = LoggerFactory.getLogger(DataTest.class);

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void test() throws Exception {
        List<SysUser> sysUserList = this.sysUserService.list();
        sysUserList.forEach(user -> {
            logger.info("user = {}", user);
        });
        BaseMapper<SysUser> sysUserMapper = this.sysUserService.getBaseMapper();
        logger.info("sysUserMapper = {}", sysUserMapper);
    }
}
