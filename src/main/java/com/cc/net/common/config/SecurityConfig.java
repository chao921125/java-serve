package com.cc.net.common.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.naming.AuthenticationException;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

//    @Bean
//    public JwtRe
}
