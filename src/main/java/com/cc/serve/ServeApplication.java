package com.cc.serve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServeApplication {
    private static final Logger logger = LoggerFactory.getLogger(ServeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServeApplication.class, args);
        logger.info("******服务启动成功******");
    }

}
