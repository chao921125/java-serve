package com.cc.serve.config;

import org.springdoc.webmvc.core.providers.SpringWebMvcProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Import({ WebSecurityConfigurer.class})
@EnableGlobalAuthentication
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // 禁用 CSRF（仅供演示，根据需要配置）
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll() // 对 /public/** 开放
                        .anyRequest().authenticated() // 其他请求需要认证
                ); // 启用表单登录

        return http.build();
    }
}
