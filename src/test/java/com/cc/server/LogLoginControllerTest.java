package com.cc.server;

import com.cc.server.entity.log.LogLogin;
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
public class LogLoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRecordAndQueryNormalLoginLog() throws Exception {
        LogLogin log = new LogLogin();
        log.setUserId("1");
        log.setUserName("admin");
        log.setIp("127.0.0.1");
        log.setLoginTime(new Date());
        log.setStatus("0");
        log.setMessage("登录成功");
        String json = objectMapper.writeValueAsString(log);
        mockMvc.perform(post("/log-login/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        // 查询
        MvcResult result = mockMvc.perform(get("/log-login/list"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("登录成功"));
    }

    @Test
    void testRecordAndQueryExceptionLoginLog() throws Exception {
        LogLogin log = new LogLogin();
        log.setUserId("2");
        log.setUserName("test");
        log.setIp("127.0.0.1");
        log.setLoginTime(new Date());
        log.setStatus("1");
        log.setMessage("登录失败");
        log.setExceptionMsg("BadCredentialsException");
        String json = objectMapper.writeValueAsString(log);
        mockMvc.perform(post("/log-login/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        // 按条件查询
        String queryJson = objectMapper.writeValueAsString(log);
        MvcResult result = mockMvc.perform(post("/log-login/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(queryJson))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("BadCredentialsException"));
    }
} 