package com.cc.server.controller.monitor;

import com.cc.frame.constants.CacheKey;
import com.cc.server.vo.monitor.OnlineUserVO;
import com.cc.server.vo.PageRequest;
import com.cc.server.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api-admin/online-user")
public class OnlineUserController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/list")
    public PageResult<OnlineUserVO> list(@RequestParam(defaultValue = "1") int pageNum, 
                                        @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        Set<String> keys = stringRedisTemplate.keys(CacheKey.LOGIN_TOKEN_KEY + "*");
        if (keys == null) return new PageResult<>(0, Collections.emptyList());
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
        int total = users.size();
        int fromIndex = Math.min((pageRequest.getPageNum() - 1) * pageRequest.getPageSize(), total);
        int toIndex = Math.min(fromIndex + pageRequest.getPageSize(), total);
        List<OnlineUserVO> pageList = users.subList(fromIndex, toIndex);
        return new PageResult<>(total, pageList);
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