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
import java.util.List;

@RestController
@RequestMapping("/admin/sys-config")
public class SysConfigController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SysConfigService sysConfigService;

    private static final String CONFIG_PREFIX = "sys_config:";

    // 获取配置参数
    @GetMapping("/get")
    public Map<String, String> getConfig(@RequestParam String key) {
        String value = stringRedisTemplate.opsForValue().get(CONFIG_PREFIX + key);
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("value", value);
        return map;
    }

    // 设置配置参数（可选过期时间，单位秒）
    @PostMapping("/set")
    public String setConfig(@RequestParam String key, @RequestParam String value, @RequestParam(required = false) Long expireSeconds) {
        if (expireSeconds != null && expireSeconds > 0) {
            stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + key, value, expireSeconds, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + key, value);
        }
        return "success";
    }

    // 获取token有效期（天）
    @GetMapping("/token-expire-days")
    public Map<String, String> getTokenExpireDays() {
        String value = stringRedisTemplate.opsForValue().get(CONFIG_PREFIX + "token-expire-days");
        Map<String, String> map = new HashMap<>();
        map.put("token-expire-days", value);
        return map;
    }

    // 设置token有效期（天）
    @PostMapping("/token-expire-days")
    public String setTokenExpireDays(@RequestParam int days) {
        stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + "token-expire-days", String.valueOf(days));
        return "success";
    }

    // 查询所有配置
    @GetMapping("/list")
    public List<SysConfig> list() {
        return sysConfigService.selectAllSysConfig();
    }

    // 新增配置
    @PostMapping("/add")
    public String add(@RequestBody SysConfig config) {
        sysConfigService.insertSysConfig(config);
        stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + config.getConfigKey(), config.getConfigValue());
        return "success";
    }

    // 修改配置
    @PostMapping("/update")
    public String update(@RequestBody SysConfig config) {
        sysConfigService.updateSysConfigById(config);
        stringRedisTemplate.opsForValue().set(CONFIG_PREFIX + config.getConfigKey(), config.getConfigValue());
        return "success";
    }

    // 删除配置
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        SysConfig config = sysConfigService.selectSysConfigById(id);
        if (config != null) {
            stringRedisTemplate.delete(CONFIG_PREFIX + config.getConfigKey());
        }
        sysConfigService.deleteSysConfigById(id);
        return "success";
    }
} 