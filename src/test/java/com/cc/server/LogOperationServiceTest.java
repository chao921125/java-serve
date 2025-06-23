package com.cc.server;

import com.cc.server.entity.log.LogOperation;
import com.cc.server.service.log.LogOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class LogOperationServiceTest {
    @Autowired
    private LogOperationService logOperationService;

    @Test
    void testInsertAndSelectLogOperation() {
        LogOperation log = new LogOperation();
        log.setMessage("testop");
        Integer result = logOperationService.insertLogOperation(log);
        Assertions.assertTrue(result > 0);
        List<LogOperation> logs = logOperationService.selectLogOperationByCondition(log);
        Assertions.assertFalse(logs.isEmpty());
    }

    @Test
    void testUpdateLogOperation() {
        LogOperation log = new LogOperation();
        log.setMessage("updateop");
        logOperationService.insertLogOperation(log);
        log.setMessage("updatedop");
        Integer result = logOperationService.updateLogOperationById(log);
        Assertions.assertTrue(result >= 0);
    }

    @Test
    void testDeleteLogOperation() {
        LogOperation log = new LogOperation();
        log.setMessage("deleteop");
        logOperationService.insertLogOperation(log);
        List<LogOperation> logs = logOperationService.selectLogOperationByCondition(log);
        if (!logs.isEmpty()) {
            Long id = logs.get(0).getId();
            Integer result = logOperationService.deleteLogOperationById(id);
            Assertions.assertTrue(result >= 0);
        }
    }
} 