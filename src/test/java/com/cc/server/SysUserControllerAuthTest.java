package com.cc.server;

import com.cc.frame.config.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SysUserControllerAuthTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtil jwtUtil;

    private String genToken(String username, String authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities);
        return jwtUtil.generateToken(claims, username);
    }

    @Test
    void testNoTokenDenied() throws Exception {
        mockMvc.perform(get("/api-admin/sys-user/user?username=any"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testNoPermissionDenied() throws Exception {
        String token = genToken("user1", "sys:user:other");
        mockMvc.perform(get("/api-admin/sys-user/user?username=any")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void testHasPermissionAllowed() throws Exception {
        String token = genToken("user2", "sys:user:query");
        mockMvc.perform(get("/api-admin/sys-user/user?username=any")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
} 