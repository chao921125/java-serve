package com.cc.server;

//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.cc.server.model.entity.system.SysUser;
//import com.cc.server.service.system.SysUserService;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import jakarta.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@SpringBootTest
class ServerApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(ServerApplicationTests.class);

//    @Resource
//    private SysUserService sysUserService;

	@Test
	void contextLoads() {
	}

//    @Test
//    public void test() throws Exception {
//        List<SysUser> sysUserList = this.sysUserService.list();
//        sysUserList.forEach(user -> {
//            logger.info("user = {}", user);
//        });
//        BaseMapper<SysUser> sysUserMapper = this.sysUserService.getBaseMapper();
//        logger.info("sysUserMapper = {}", sysUserMapper);
//    }
}
