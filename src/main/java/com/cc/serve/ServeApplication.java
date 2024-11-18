package com.cc.serve;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.cc.serve.mapper")
public class ServeApplication {
    private static final Logger logger = LoggerFactory.getLogger(ServeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServeApplication.class, args);
        logger.info("Server Success!");
    }

}
