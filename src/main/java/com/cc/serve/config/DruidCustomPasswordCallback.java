package com.cc.serve.config;

import com.alibaba.druid.util.DruidPasswordCallback;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Slf4j
public class DruidCustomPasswordCallback extends DruidPasswordCallback {
    private final static Logger logger = LoggerFactory.getLogger(DruidCustomPasswordCallback.class);

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String pwd = (String) properties.getProperty("password");
        if (StringUtils.hasLength(pwd)) {
            try {
                String password = "root123";
                setPassword(password.toCharArray());
            } catch (Exception e) {
                logger.error(e.toString());
            }
        }
    }
}

