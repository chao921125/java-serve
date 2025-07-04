package com.cc.server.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;
import com.cc.server.entity.system.SysConfig;
import com.cc.server.service.system.SysConfigService;
import jakarta.annotation.Resource;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.ApiResponse;

@RestController
@RequestMapping("/api-admin/sys-config")
public class SysConfigController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SysConfigService sysConfigService;

    private static final String CONFIG_PREFIX = "sys_config:";

    // 获取配置参数
    @GetMapping("/get")
    public ApiResponse<Map<String, String>> getConfig(@RequestParam String key) {
        String value = stringRedisTemplate.opsForValue().get(CONFIG_PREFIX + key);
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("value", value);
        return ApiResponse.success(map);
    }

    // 设置配置参数（可选过期时间，单位秒）
    @PostMapping("/set")
    public ApiResponse<String> setConfig(@RequestParam String key, @RequestParam String value, @RequestParam(required = false) Long expireSeconds) {
        if (expireSeconds != null && expireSeconds > 0) {
            stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + key, value, expireSeconds, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + key, value);
        }
        return ApiResponse.success("设置成功", null);
    }

    // 获取token有效期（天）
    @GetMapping("/token-expire-days")
    public ApiResponse<Map<String, String>> getTokenExpireDays() {
        String value = stringRedisTemplate.opsForValue().get(CONFIG_PREFIX + "token-expire-days");
        Map<String, String> map = new HashMap<>();
        map.put("token-expire-days", value);
        return ApiResponse.success(map);
    }

    // 设置token有效期（天）
    @PostMapping("/token-expire-days")
    public ApiResponse<String> setTokenExpireDays(@RequestParam int days) {
        stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + "token-expire-days", String.valueOf(days));
        return ApiResponse.success("设置成功", null);
    }

    // 查询所有配置
    @GetMapping("/list")
    public ApiResponse<PageResult<SysConfig>> list(@RequestParam(defaultValue = "1") int pageNum, 
                                     @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        return ApiResponse.success(sysConfigService.pageSysConfig(pageRequest));
    }

    // 新增配置
    @PostMapping("/add")
    public ApiResponse<String> add(@RequestBody SysConfig config) {
        sysConfigService.insertSysConfig(config);
        stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + config.getConfigKey(), config.getConfigValue());
        return ApiResponse.success("新增成功", null);
    }

    // 修改配置
    @PostMapping("/update")
    public ApiResponse<String> update(@RequestBody SysConfig config) {
        sysConfigService.updateSysConfigById(config);
        stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + config.getConfigKey(), config.getConfigValue());
        return ApiResponse.success("修改成功", null);
    }

    // 删除配置
    @PostMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        SysConfig config = sysConfigService.selectSysConfigById(id);
        if (config != null) {
            stringRedisTemplate.delete(CONFIG_PREFIX + config.getConfigKey());
        }
        sysConfigService.deleteSysConfigById(id);
        return ApiResponse.success("删除成功", null);
    }
} 