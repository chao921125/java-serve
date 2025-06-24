package com.cc.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class OnlineUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/online-user/list?pageNum=1&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").exists())
                .andExpect(jsonPath("$.rows").isArray());
    }
} 