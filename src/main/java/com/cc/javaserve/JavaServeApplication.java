package com.cc.javaserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class JavaServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaServeApplication.class, args);
    }

}
