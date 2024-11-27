package com.cc.serve.config.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application")
public class JwtUtil {
    @Value("${application.security.jwt.secret}")
    private String JWT_KEY;
    @Value("${application.security.jwt.expirat}")
    public Long JWT_TTL;

    public static String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userName);

        return Jwts.builder().claims().add(claims).toString();
    }
}
