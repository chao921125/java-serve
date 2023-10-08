package com.cc.net;

import com.cc.net.entity.log.LogLogin;
import com.cc.net.mapper.log.LogLoginMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LogTest {
    private LogLoginMapper logLoginMapper;

    @Test
    public void testList() {
        List<LogLogin> list = logLoginMapper.selectList(null);
        list.forEach(System.out::println);
    }
}
