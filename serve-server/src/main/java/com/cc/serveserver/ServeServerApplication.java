package com.cc.serveserver;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.cc.serveserver.mapper")
public class ServeServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(ServeServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServeServerApplication.class, args);
        logger.info("Server Success!");
    }

}
