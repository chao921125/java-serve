package com.cc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 应用启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.cc"})
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        System.out.println("""
                
                ===========================================
                   Java Serve Admin - 启动成功!
                   API 文档: http://localhost:8080/docs
                ===========================================
                """);
    }
}
