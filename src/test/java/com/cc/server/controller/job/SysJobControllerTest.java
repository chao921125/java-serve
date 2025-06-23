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
public class SysJobControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testJobPageAndCRUD() throws Exception {
        // 新增
        String addJson = "{" +
                "\"jobName\":\"测试任务\"," +
                "\"jobGroup\":\"DEFAULT\"," +
                "\"invokeTarget\":\"testTarget\"," +
                "\"cronExpression\":\"0/30 * * * * ?\"," +
                "\"misfirePolicy\":\"0\"," +
                "\"concurrent\":\"0\"," +
                "\"status\":\"0\"," +
                "\"remark\":\"集成测试\"}";
        mockMvc.perform(post("/job/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("success"));

        // 分页查询
        mockMvc.perform(get("/job/page?pageNum=1&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        // 条件查询
        mockMvc.perform(get("/job/page?pageNum=1&pageSize=5&jobName=测试"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        // 获取详情
        MvcResult pageResult = mockMvc.perform(get("/job/page?pageNum=1&pageSize=1&jobName=测试"))
                .andReturn();
        String pageContent = pageResult.getResponse().getContentAsString();
        Long id = objectMapper.readTree(pageContent).get("rows").get(0).get("id").asLong();
        mockMvc.perform(get("/job/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("success"));

        // 修改
        String updateJson = "{" +
                "\"id\":" + id + "," +
                "\"jobName\":\"测试任务2\"," +
                "\"jobGroup\":\"DEFAULT\"," +
                "\"invokeTarget\":\"testTarget\"," +
                "\"cronExpression\":\"0/30 * * * * ?\"," +
                "\"misfirePolicy\":\"0\"," +
                "\"concurrent\":\"0\"," +
                "\"status\":\"0\"," +
                "\"remark\":\"集成测试修改\"}";
        mockMvc.perform(put("/job/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("success"));

        // 暂停
        mockMvc.perform(post("/job/pause/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("success"));
        // 恢复
        mockMvc.perform(post("/job/resume/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("success"));
        // 立即执行
        mockMvc.perform(post("/job/runOnce/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("success"));
        // 删除
        mockMvc.perform(delete("/job/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("success"));
    }
} 