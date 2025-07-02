package com.cc.server;

import com.cc.frame.utils.StringUtil;
import com.cc.server.service.system.SysUserService;
import com.cc.server.vo.system.SysUserVO;
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
public class LoginLogicTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private SysUserService userService;

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
    void testValidateUserLogin() {
        // 测试正确的用户名和密码
        SysUserVO user = userService.validateUserLogin("admin", "123123");
        System.out.println("验证admin用户登录结果: " + (user != null ? "成功" : "失败"));
        
        // 测试错误的密码
        SysUserVO wrongUser = userService.validateUserLogin("admin", "wrongpassword");
        System.out.println("验证错误密码结果: " + (wrongUser != null ? "成功" : "失败"));
        assert wrongUser == null;
        
        // 测试不存在的用户
        SysUserVO nonExistUser = userService.validateUserLogin("nonexistent", "123123");
        System.out.println("验证不存在用户结果: " + (nonExistUser != null ? "成功" : "失败"));
        assert nonExistUser == null;
    }

    @Test
    void testGetUserByLoginName() {
        // 测试通过用户名查询
        SysUserVO userByUsername = userService.getUserByLoginName("admin");
        System.out.println("通过用户名查询结果: " + (userByUsername != null ? "成功" : "失败"));
        
        // 测试通过邮箱查询（如果admin用户有邮箱的话）
        if (userByUsername != null && userByUsername.getEmail() != null) {
            SysUserVO userByEmail = userService.getUserByLoginName(userByUsername.getEmail());
            System.out.println("通过邮箱查询结果: " + (userByEmail != null ? "成功" : "失败"));
        }
        
        // 测试通过手机号查询（如果admin用户有手机号的话）
        if (userByUsername != null && userByUsername.getPhone() != null) {
            SysUserVO userByPhone = userService.getUserByLoginName(userByUsername.getPhone());
            System.out.println("通过手机号查询结果: " + (userByPhone != null ? "成功" : "失败"));
        }
    }

    @Test
    void testCheckUserExists() {
        // 测试检查用户是否存在
        SysUserVO existUser = userService.checkUserExists("admin", null, null);
        System.out.println("检查admin用户是否存在: " + (existUser != null ? "存在" : "不存在"));
        
        // 测试检查不存在的用户
        SysUserVO nonExistUser = userService.checkUserExists("nonexistent", null, null);
        System.out.println("检查不存在用户是否存在: " + (nonExistUser != null ? "存在" : "不存在"));
        assert nonExistUser == null;
    }

    @Test
    void testLoginWithAdminUser() throws Exception {
        String loginJson = "{\"userName\":\"admin\",\"password\":\"123123\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void testLoginWithWrongPassword() throws Exception {
        String loginJson = "{\"userName\":\"admin\",\"password\":\"wrongpassword\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.msg").value("用户名/邮箱/手机号或密码错误"));
    }

    @Test
    void testLoginWithEmptyUsername() throws Exception {
        String loginJson = "{\"userName\":\"\",\"password\":\"123123\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("登录名不能为空"));
    }

    @Test
    void testLoginWithEmptyPassword() throws Exception {
        String loginJson = "{\"userName\":\"admin\",\"password\":\"\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("密码不能为空"));
    }

    @Test
    void testLoginWithNullUsername() throws Exception {
        String loginJson = "{\"password\":\"123123\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("登录名不能为空"));
    }

    @Test
    void testLoginWithNullPassword() throws Exception {
        String loginJson = "{\"userName\":\"admin\"}";
        
        mockMvc.perform(post("/api-admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("密码不能为空"));
    }
} 