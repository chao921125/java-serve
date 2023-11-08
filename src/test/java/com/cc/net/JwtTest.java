package com.cc.net;

import com.cc.net.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {

    @Test
    public void testJwt() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("admin|63743kwerhwekr234o23o90283497werk");
        System.out.println("token = " + token);

        Claims claims = jwtUtil.getClaimsByToken(token);
        System.out.println("claims = " + claims);

        String username = jwtUtil.getClaimFiled(token, "user");
        System.out.println("username = " + username);
    }
}
