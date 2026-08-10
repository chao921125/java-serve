package com.cc.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;
import java.util.Collections;

/**
 * 代码生成器
 * 根据数据库表结构自动生成 Entity、Mapper、Service、Controller 代码
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/serve?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String author = "CC Admin";

        // 要生成的表名（多个表用逗号分隔）
        String tables = "sys_user";
        // 表前缀（生成时会去掉）
        String tablePrefix = "sys_";
        // 包名
        String packageName = "com.cc";
        // 模块名
        String moduleName = "core";

        // 获取项目根目录
        String projectPath = Paths.get("").toAbsolutePath().toString();

        FastAutoGenerator.create(url, username, password)
                // 全局配置
                .globalConfig(builder -> builder
                        .author(author)
                        .outputDir(projectPath + "/generator/src/main/java")
                        .disableOpenDir()
                        .commentDate("yyyy-MM-dd"))
                // 包配置
                .packageConfig(builder -> builder
                        .parent(packageName)
                        .moduleName(moduleName)
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .pathInfo(Collections.singletonMap(
                                OutputFile.xml,
                                projectPath + "/generator/src/main/resources/mapper")))
                // 策略配置
                .strategyConfig(builder -> builder
                        .addInclude(tables.split(","))
                        .addTablePrefix(tablePrefix)
                        // Entity 策略
                        .entityBuilder()
                        .enableLombok()
                        .enableChainModel()
                        .enableTableFieldAnnotation()
                        // Mapper 策略
                        .mapperBuilder()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        // Service 策略
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        // Controller 策略
                        .controllerBuilder()
                        .enableRestStyle())
                // 模板引擎
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

        System.out.println("代码生成完成！请检查 generator 模块。");
    }
}
