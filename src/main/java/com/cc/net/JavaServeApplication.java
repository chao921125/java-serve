package com.cc.net;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "com.cc.net.mapper")
public class JavaServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaServeApplication.class, args);
    }

}