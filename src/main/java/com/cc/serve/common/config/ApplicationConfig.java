package com.cc.serve.common.config;

import com.cc.serve.mapper.system.SysUserMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

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
