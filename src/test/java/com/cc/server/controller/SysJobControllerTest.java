package com.cc.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class SysJobControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/job/list?pageNum=1&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").exists())
                .andExpect(jsonPath("$.rows").isArray());
    }

    @Test
    void testAdd() throws Exception {
        String json = "{\"jobName\":\"测试任务\",\"jobGroup\":\"DEFAULT\"}";
        mockMvc.perform(post("/job/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        String json = "{\"id\":1,\"jobName\":\"修改任务\"}";
        mockMvc.perform(post("/job/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(post("/job/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDetail() throws Exception {
        mockMvc.perform(get("/job/1"))
                .andExpect(status().isOk());
    }
} 