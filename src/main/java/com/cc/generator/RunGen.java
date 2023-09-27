package com.cc.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
public class RunGen {
    public static void main(String[] args) {
        final String DATA_SOURCE = "jdbc:mysql://localhost:3306/cc?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        final String DATA_NAME = "root";
        final String DATA_PWD = "root123456";
        final String PKG = "com.cc.javaserve";

        final String TABLE_NAME = "sys_user";
        final String TABLE_NAME_PREFIX = "";

        FastAutoGenerator.create(DATA_SOURCE, DATA_NAME, DATA_PWD)
                .globalConfig(builder -> {
                    builder.author("cc") // 作者
                            .commentDate("yyyy-mm-dd HH:MM:SS")// 时间
                            .outputDir(System.getProperty("user.dir") + "/src/main/java/com/cc/generator")// 目录
                            .disableOpenDir();// 禁止打开目录
                })
                .packageConfig(builder -> {
                    builder.parent(PKG) // 设置父包，mapper路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/java/com/cc/generator/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_NAME) // 表
                            .addTablePrefix(TABLE_NAME_PREFIX) // 过滤表前缀
                            .entityBuilder() // Entity策略
                            .enableLombok() //
                            .enableFileOverride() // 覆盖已经生成的
                            .naming(NamingStrategy.underline_to_camel) // 数据库映射实体，驼峰
                            .columnNaming(NamingStrategy.underline_to_camel) // 字段映射，驼峰
                            .mapperBuilder() // Mapper策略
                            .enableFileOverride() // 覆盖
                            .serviceBuilder() // Service策略
                            .enableFileOverride() // 覆盖
                            .formatServiceFileName("I%sService") // %s匹配表名，接口文件名
                            .formatServiceImplFileName("%sServiceImpl")
                            .controllerBuilder() // Controller策略
                            .enableFileOverride(); // 覆盖
                })
                .execute();
    }
}
