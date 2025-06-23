package com.cc.server.controller.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SysJobLogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testJobLogPageAndDetail() throws Exception {
        // 先查一条任务，获取jobId
        MvcResult jobPageResult = mockMvc.perform(get("/job/page?pageNum=1&pageSize=1"))
                .andReturn();
        String jobPageContent = jobPageResult.getResponse().getContentAsString();
        if (objectMapper.readTree(jobPageContent).get("rows").size() == 0) return;
        Long jobId = objectMapper.readTree(jobPageContent).get("rows").get(0).get("id").asLong();

        // 日志分页
        mockMvc.perform(get("/job/log/page?pageNum=1&pageSize=5&jobId=" + jobId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        // 按任务ID查日志
        mockMvc.perform(get("/job/log/list/" + jobId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("success"));

        // 获取日志详情（如有日志）
        MvcResult logPageResult = mockMvc.perform(get("/job/log/page?pageNum=1&pageSize=1&jobId=" + jobId))
                .andReturn();
        String logPageContent = logPageResult.getResponse().getContentAsString();
        if (objectMapper.readTree(logPageContent).get("rows").size() > 0) {
            Long logId = objectMapper.readTree(logPageContent).get("rows").get(0).get("id").asLong();
            mockMvc.perform(get("/job/log/" + logId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("success"));
        }
    }
} 