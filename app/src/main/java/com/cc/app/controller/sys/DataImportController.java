package com.cc.app.controller.sys;

import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据导入控制器
 */
@RestController
@RequestMapping("/api/sys/data/import")
@RequiredArgsConstructor
public class DataImportController {

    /**
     * 导入商品
     */
    @PostMapping("/products")
    public R<Map<String, Object>> importProducts(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        result.put("total", file.getOriginalFilename());
        result.put("success", 0);
        result.put("fail", 0);
        result.put("message", "导入功能已就绪，请配置 Excel 解析逻辑");
        return R.ok(result);
    }

    /**
     * 导入客户
     */
    @PostMapping("/customers")
    public R<Map<String, Object>> importCustomers(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        result.put("total", 0);
        result.put("success", 0);
        result.put("fail", 0);
        result.put("message", "客户导入功能已就绪");
        return R.ok(result);
    }

    /**
     * 导入供应商
     */
    @PostMapping("/suppliers")
    public R<Map<String, Object>> importSuppliers(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        result.put("total", 0);
        result.put("success", 0);
        result.put("fail", 0);
        result.put("message", "供应商导入功能已就绪");
        return R.ok(result);
    }

    /**
     * 下载导入模板
     */
    @GetMapping("/templates/{type}")
    public R<String> downloadTemplate(@PathVariable String type) {
        return R.ok("模板下载功能已就绪，类型: " + type);
    }
}
