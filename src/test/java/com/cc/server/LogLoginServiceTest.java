package com.cc.server;

import com.cc.server.entity.log.LogLogin;
import com.cc.server.service.log.LogLoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class LogLoginServiceTest {
    @Autowired
    private LogLoginService logLoginService;

    @Test
    void testInsertAndSelectLogLogin() {
        LogLogin log = new LogLogin();
        log.setUserName("testuser");
        Integer result = logLoginService.insertLogLogin(log);
        Assertions.assertTrue(result > 0);
        List<LogLogin> logs = logLoginService.selectLogLoginByCondition(log);
        Assertions.assertFalse(logs.isEmpty());
    }

    @Test
    void testUpdateLogLogin() {
        LogLogin log = new LogLogin();
        log.setUserName("updateuser");
        logLoginService.insertLogLogin(log);
        log.setUserName("updateduser");
        Integer result = logLoginService.updateLogLoginById(log);
        Assertions.assertTrue(result >= 0);
    }

    @Test
    void testDeleteLogLogin() {
        LogLogin log = new LogLogin();
        log.setUserName("deleteuser");
        logLoginService.insertLogLogin(log);
        List<LogLogin> logs = logLoginService.selectLogLoginByCondition(log);
        if (!logs.isEmpty()) {
            Long id = logs.get(0).getId();
            Integer result = logLoginService.deleteLogLoginById(id);
            Assertions.assertTrue(result >= 0);
        }
    }
} 