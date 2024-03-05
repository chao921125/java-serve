package com.cc.serve.controller.log;

import com.cc.serve.entity.log.LogLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cc
 * @since 2023-42-08 14:10:202
 */
@RestController
@RequestMapping("/log-login")
@Slf4j
@Tag(name = "日志-登录")
public class LogLoginController {
    @PostMapping("/list")
    @Operation(summary = "日志列表")
    public ArrayList<LogLogin> getListPage() {
        return new ArrayList<LogLogin>();
    }

    @GetMapping("/get")
    @Operation(summary = "测试")
    public String getOne() {
        return "123";
    }
}
