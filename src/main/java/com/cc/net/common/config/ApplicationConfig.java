package com.cc.net.common.config;

import com.cc.net.mapper.system.SysUserMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Resource
    SysUserMapper sysUserMapper;

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return username -> sysUserMapper.selectUserByUserName(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username + "not found in db"));
//    }
}
