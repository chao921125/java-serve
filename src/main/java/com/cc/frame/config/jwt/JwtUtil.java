package com.cc.frame.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {
	@Value("${application.security.jwt.secret}")
	private String JWT_KEY;
	@Value("${application.security.jwt.expirat}")
	public Long JWT_TTL;

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 提取所有声明
	 *
	 * @param token 令牌
	 * @return {@code Claims}
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	/**
	 * 获取签名密钥（从配置文件读取Base64字符串并转换为SecretKey）
	 *
	 * @return {@code SecretKey}
	 */
	private SecretKey getSignInKey() {
		try {
			byte[] keyBytes = java.util.Base64.getDecoder().decode(JWT_KEY);
			if (keyBytes.length < 64) {
				System.err.println("JWT密钥长度不足，HS512算法要求至少64字节（512位），请更换更长的密钥！");
			}
			return Keys.hmacShaKeyFor(keyBytes);
		} catch (Exception e) {
			System.err.println("JWT密钥解析失败，请检查配置文件中的JWT密钥格式！");
			throw new RuntimeException("JWT密钥无效", e);
		}
	}

	/**
	 * 从Jwt提取特定声明（Claims）信息
	 *
	 * @param token          令牌
	 * @param claimsResolver 要求解析器
	 * @return {@code T}
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * 生成Jwt令牌
	 *
	 * @param extraClaims 额外的声明信息
	 * @param userName    用户详细信息
	 * @return {@code String}
	 */
	public String generateToken(Map<String, Object> extraClaims, String userName) {
		return Jwts.builder()
				.claims(extraClaims)
				.subject(userName)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + JWT_TTL))
				.signWith(getSignInKey())
				.compact();
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
	 * @param token    令牌
	 * @param userName 用户详细信息
	 * @return boolean
	 */
	public boolean isTokenValid(String token, String userName) {
		try {
			final String extractedUserName = extractClaim(token, Claims::getSubject);
			return (extractedUserName.equals(userName) && !isTokenExpired(token));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检查令牌是否过期
	 *
	 * @param token 令牌
	 * @return boolean
	 */
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	/**
	 * 生成安全的JWT密钥（仅供开发时生成配置用密钥，不在主业务逻辑中调用）
	 *
	 * @return Base64编码的安全密钥
	 */
	public static String generateSecureKey() {
		// 仅供开发时生成密钥用，主业务逻辑不调用此方法
		javax.crypto.SecretKey key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());
		return java.util.Base64.getEncoder().encodeToString(key.getEncoded());
	}

	// public static void main(String[] args) {
	// 	System.out.println(generateSecureKey());
	// }
}
