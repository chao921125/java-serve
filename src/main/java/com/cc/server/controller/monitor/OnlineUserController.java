package com.cc.server.controller.monitor;

import com.cc.frame.constants.CacheKey;
import com.cc.server.vo.monitor.OnlineUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/online-user")
public class OnlineUserController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/list")
    public List<OnlineUserVO> list() {
        Set<String> keys = stringRedisTemplate.keys(CacheKey.LOGIN_TOKEN_KEY + "*");
        if (keys == null) return Collections.emptyList();
        List<OnlineUserVO> users = new ArrayList<>();
        for (String key : keys) {
            String json = stringRedisTemplate.opsForValue().get(key);
            if (json != null) {
                try {
                    OnlineUserVO vo = com.alibaba.fastjson2.JSON.parseObject(json, OnlineUserVO.class);
                    users.add(vo);
                } catch (Exception ignored) {}
            }
        }
        return users;
    }

    @GetMapping("/kick/{userId}")
    public String kick(@PathVariable Long userId) {
        Set<String> keys = stringRedisTemplate.keys(CacheKey.LOGIN_TOKEN_KEY + "*");
        if (keys != null) {
            for (String key : keys) {
                String json = stringRedisTemplate.opsForValue().get(key);
                if (json != null && json.contains("\"userId\":" + userId)) {
                    stringRedisTemplate.delete(key);
                }
            }
        }
        return "success";
    }
} 