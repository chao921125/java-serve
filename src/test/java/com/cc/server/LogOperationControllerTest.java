package com.cc.server;

import com.cc.server.entity.log.LogOperation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Date;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LogOperationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRecordAndQueryNormalLog() throws Exception {
        LogOperation log = new LogOperation();
        log.setUserId("1");
        log.setUserName("admin");
        log.setIp("127.0.0.1");
        log.setOperTime(new Date());
        log.setStatus("0");
        log.setType("NORMAL");
        log.setMessage("正常操作");
        String json = objectMapper.writeValueAsString(log);
        mockMvc.perform(post("/log-operation/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        // 查询
        MvcResult result = mockMvc.perform(get("/log-operation/list"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("正常操作"));
    }

    @Test
    void testRecordAndQueryExceptionLog() throws Exception {
        LogOperation log = new LogOperation();
        log.setUserId("2");
        log.setUserName("test");
        log.setIp("127.0.0.1");
        log.setOperTime(new Date());
        log.setStatus("1");
        log.setType("EXCEPTION");
        log.setMessage("异常操作");
        log.setExceptionMsg("NullPointerException");
        String json = objectMapper.writeValueAsString(log);
        mockMvc.perform(post("/log-operation/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        // 按条件查询
        String queryJson = objectMapper.writeValueAsString(log);
        MvcResult result = mockMvc.perform(post("/log-operation/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(queryJson))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("NullPointerException"));
    }
} 