package com.cc.server;

import com.cc.frame.utils.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthLoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testMD5Calculation() {
        // 验证MD5计算是否正确
        String password123123 = StringUtil.md5("123123");
        String password123456 = StringUtil.md5("123456");
        
        System.out.println("123123的MD5值: " + password123123);
        System.out.println("123456的MD5值: " + password123456);
        
        assert password123123.equals("4297f44b13955235245b2497399d7a93");
        assert password123456.equals("e10adc3949ba59abbe56e057f20f883e");
    }

    @Test
    void testLoginWithTestUser() throws Exception {
        String loginJson = "{\"userName\":\"test\",\"password\":\"123123\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void testLoginWithAdminUser() throws Exception {
        String loginJson = "{\"userName\":\"admin\",\"password\":\"123456\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void testLoginWithWrongPassword() throws Exception {
        String loginJson = "{\"userName\":\"test\",\"password\":\"wrongpassword\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.msg").value("用户名/邮箱/手机号或密码错误"));
    }

    @Test
    void testLoginWithNonExistentUser() throws Exception {
        String loginJson = "{\"userName\":\"nonexistent\",\"password\":\"123123\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.msg").value("用户名/邮箱/手机号或密码错误"));
    }
} 