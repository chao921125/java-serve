package com.cc.frame.config.security;

import com.cc.frame.config.jwt.JwtFilter;
import com.cc.frame.config.jwt.JwtUtil;
import com.cc.frame.exception.AuthException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalAuthentication
public class SecurityConfig {

	private final JwtUtil jwtUtil;
	// 白名单路径数组（可自定义）
	private static final String[] WHITE_LIST_URLS = {
			"/api-admin/auth/**",
			"/swagger-ui/**",
			"/v3/api-docs/**",
			"/docs/**",
			"/swagger-resources/**",
			"/webjars/**",
			"/api-docs/**",
			"/api/**",
			"/ui/**",
			"/api/auth/**",
			"/api/public/**"
	};

	public SecurityConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	/**
	 * 密码加密使用的编码器
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthException authenticationExceptionHandler() {
		return new AuthException();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthException authenticationExceptionHandler) throws Exception {
		http.csrf(csrf -> csrf.ignoringRequestMatchers(WHITE_LIST_URLS)) // 禁用 CSRF
				.cors(AbstractHttpConfigurer::disable) // 禁用 CORS
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(WHITE_LIST_URLS).permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.logout(AbstractHttpConfigurer::disable)
				.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(authenticationExceptionHandler));

		return http.build();
	}
}
