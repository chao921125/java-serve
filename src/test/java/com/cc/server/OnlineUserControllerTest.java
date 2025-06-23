package com.cc.server;

import com.cc.frame.constants.CacheKey;
import com.cc.server.vo.monitor.OnlineUserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Date;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OnlineUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListAndKickOnlineUser() throws Exception {
        // 模拟写入一个在线用户
        OnlineUserVO vo = new OnlineUserVO();
        vo.setUserId(100L);
        vo.setUserName("testuser");
        vo.setToken("token-abc");
        vo.setLoginTime(new Date());
        vo.setLastActiveTime(new Date());
        vo.setIp("127.0.0.1");
        vo.setStatus("online");
        String json = com.alibaba.fastjson2.JSON.toJSONString(vo);
        String key = CacheKey.LOGIN_TOKEN_KEY + vo.getToken();
        stringRedisTemplate.opsForValue().set(key, json);

        // 查询在线用户
        MvcResult result = mockMvc.perform(get("/online-user/list"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("testuser"));

        // 踢出用户
        mockMvc.perform(delete("/online-user/kick/100"))
                .andExpect(status().isOk());
        // 再查应无此用户
        result = mockMvc.perform(get("/online-user/list"))
                .andExpect(status().isOk()).andReturn();
        content = result.getResponse().getContentAsString();
        Assertions.assertFalse(content.contains("testuser"));
    }
} 