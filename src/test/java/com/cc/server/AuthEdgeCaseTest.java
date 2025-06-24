package com.cc.server;

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
public class AuthEdgeCaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginEmptyUsername() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("username", "")
                .param("password", "123456"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginEmptyPassword() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("username", "admin")
                .param("password", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWrongPassword() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("username", "admin")
                .param("password", "wrong"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginUserNotExist() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("username", "not_exist_user")
                .param("password", "123456"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testNoTokenAccess() throws Exception {
        mockMvc.perform(get("/admin/sys-user/user?username=admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testTokenNoPermission() throws Exception {
        String token = "无权限用户的token";
        mockMvc.perform(get("/admin/sys-user/user?username=admin")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void testTokenFormatError() throws Exception {
        mockMvc.perform(get("/admin/sys-user/user?username=admin")
                .header("Authorization", "token-format-error"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginDisabledUser() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("username", "disabled_user")
                .param("password", "123456"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testLoginUsernameTooShort() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("username", "a")
                .param("password", "123456"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginPasswordTooShort() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("username", "admin")
                .param("password", "123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginUsernameTooLong() throws Exception {
        String longUsername = "a".repeat(30);
        mockMvc.perform(post("/admin/sys-user/login")
                .param("username", longUsername)
                .param("password", "123456"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTokenExpired() throws Exception {
        String expiredToken = "过期的token";
        mockMvc.perform(get("/admin/sys-user/user?username=admin")
                .header("Authorization", "Bearer " + expiredToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testTokenUserDisabledAfterLogin() throws Exception {
        String token = "被禁用用户的有效token";
        mockMvc.perform(get("/admin/sys-user/user?username=disabled_user")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void testLoginByUsername() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("loginName", "admin")
                .param("password", "123456"))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginByEmail() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("loginName", "admin@example.com")
                .param("password", "123456"))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginByPhone() throws Exception {
        mockMvc.perform(post("/admin/sys-user/login")
                .param("loginName", "13800000000")
                .param("password", "123456"))
                .andExpect(status().isOk());
    }

    // 你可以根据实际业务继续补充：
    // - 被禁用用户登录
    // - 密码/用户名长度边界
    // - Token过期
    // - 账号被封禁后Token仍可用
} 