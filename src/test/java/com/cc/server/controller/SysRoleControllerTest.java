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
public class SysRoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/sys-role/list?pageNum=1&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").exists())
                .andExpect(jsonPath("$.rows").isArray());
    }

    @Test
    void testAdd() throws Exception {
        String json = "{\"roleName\":\"测试角色\"}";
        mockMvc.perform(post("/sys-role/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        String json = "{\"id\":1,\"roleName\":\"修改角色\"}";
        mockMvc.perform(post("/sys-role/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(post("/sys-role/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDetail() throws Exception {
        mockMvc.perform(get("/sys-role/1"))
                .andExpect(status().isOk());
    }
} 