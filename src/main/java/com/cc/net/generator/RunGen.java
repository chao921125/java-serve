package com.cc.net.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class RunGen {
    public static void main(String[] args) {
        log.info("======开始生成======");
        CodeGen();
        log.info("======生成代码结束======");
    }

    public static void CodeGen() {
        final String DATA_SOURCE = "jdbc:mysql://localhost:3306/cc?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        final String DATA_NAME = "root";
        final String DATA_PWD = "root123456";

        final String TABLE_NAME = "sys_department,sys_menu,sys_post,sys_role,sys_user";
        final String TABLE_NAME_PREFIX = "";

        final String PKG = "";
        final String PKG_PATH = "/src/main/java/com/cc/net/generator";
        final String PKG_PATH_MAPPER = "/src/main/resources/mapper";

        FastAutoGenerator.create(DATA_SOURCE, DATA_NAME, DATA_PWD)
                .globalConfig(builder -> {
                    builder.enableSwagger()
                            .author("cc") // 作者
                            .commentDate("yyyy-mm-dd HH:MM:SS") // 时间
                            .outputDir(System.getProperty("user.dir") + PKG_PATH) // 目录
                            .dateType(DateType.ONLY_DATE)
                            .disableOpenDir() // 禁止打开目录
                            .build();
                })
                .dataSourceConfig(builder -> {
//                    builder.build();
                })
                .packageConfig(builder -> {
                    builder.parent(PKG) // 设置父包，mapper路径
                            .controller("controller")
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + PKG_PATH_MAPPER))
                            .build();
                })
                .templateConfig(builder -> {
//                    builder.disable(TemplateType.ENTITY)
//                            .controller("/templates/entity.java");
                })
                .injectionConfig(builder -> {
//                    builder.build();
                })
                .strategyConfig(builder -> {
                    builder.addInclude(getTables(TABLE_NAME)) // 表
                            .addTablePrefix(TABLE_NAME_PREFIX) // 过滤表前缀
                            .controllerBuilder() // Controller 策略
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .enableFileOverride() // Controller 覆盖
                            .entityBuilder() // Entity 策略
                            .enableLombok() //
                            .enableTableFieldAnnotation()
                            .naming(NamingStrategy.underline_to_camel) // 数据库映射实体，驼峰
                            .columnNaming(NamingStrategy.underline_to_camel) // 字段映射，驼峰
                            .enableFileOverride() // Entity 覆盖
                            .mapperBuilder() // Mapper 策略
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper")
                            .enableFileOverride() // Mapper 覆盖
                            .serviceBuilder() // Service 策略
                            .formatServiceFileName("%sService") // %s匹配表名，接口文件名
                            .formatServiceImplFileName("%sServiceImpl")
                            .enableFileOverride() // Service 覆盖
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
