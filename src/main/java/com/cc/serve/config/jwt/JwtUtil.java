package com.cc.serve.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.security.Key;

@Component
public class JwtUtil {
    @Value("${application.security.jwt.secret}")
    private String JWT_KEY;
    @Value("${application.security.jwt.expirat}")
    public Long JWT_TTL;

    /**
     * 提取所有声明
     *
     * @param token 令牌
     * @return {@code Claims}
     */
    private Claims extractAllClaims(String token) {
//        return Jwts.builder() // 获取一个Jwt解析器构建器
//                .signWith(getSignInKey()); // 设置Jwt验证签名
        return null;
    }

    /**
     * 获取签名密钥
     *
     * @return {@code Key} 用于验证Jwt签名的密钥。签名密钥必须与生成Jwt令牌时使用的密钥相同，否则无法正确验证Jwt的真实性。
     */
    private Key getSignInKey() {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 从Jwt提取特定声明（Claims）信息
     *
     * @param token          令牌
     * @param claimsResolver 要求解析器
     * @return {@code T}
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // 获取JWT令牌中的所有声明信息,存储在Claims对象中
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 生成Jwt令牌
     *
     * @param extraClaims 额外的声明信息
     * @param userName 用户详细信息
     * @return {@code String}
     */
    public String generateToken(Map<String, Object> extraClaims, String userName) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 设置JWT令牌的过期时间（24小时）
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)   // 对JWT令牌进行签名
                .compact(); // 生成最终的JWT令牌字符串
    }

    /**
     * 生成Jwt令牌
     *
     * @param userName 用户详细信息
     * @return {@code String}
     */
    public String generateToken(String userName) {
        return generateToken(new HashMap<>(), userName);
    }

    /**
     * 令牌是否有效
     *
     * @param token       令牌
     * @param userName 用户详细信息
     * @return boolean
     */
    public boolean isTokenValid(String token, String userName) {
        return (!extractClaim(token, Claims::getExpiration).before(new Date()));
    }
}