package com.cc.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class RunGenInput {
    public static void main(String[] args) {
        final String DATA_SOURCE = "jdbc:mysql://localhost:3306/cc?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        final String DATA_NAME = "root";
        final String DATA_PWD = "root123456";

        final String TABLE_NAME = "sys_user";
        final String TABLE_NAME_PREFIX = "";

        final String PKG = "system";
        final String PKG_PATH = "/src/main/java/com/cc/generator";
        final String PKG_PATH_MAPPER = "/src/main/java/com/cc/generator/mapper";

        FastAutoGenerator.create(DATA_SOURCE, DATA_NAME, DATA_PWD)
                .globalConfig((scanner, builder) -> {
                    builder.enableSwagger()
                            .author(scanner.apply("请输入作者：")) // 作者
                            .commentDate("yyyy-mm-dd HH:MM:SS") // 时间
                            .outputDir(System.getProperty("user.dir") + PKG_PATH) // 目录
                            .disableOpenDir();// 禁止打开目录
                })
                .dataSourceConfig(builder -> {})
                .packageConfig(builder -> {
                    builder.parent(PKG) // 设置父包，mapper路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + PKG_PATH_MAPPER));
                })
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all"))) // 表
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
                            .formatServiceFileName("%sService") // %s匹配表名，接口文件名
                            .formatServiceImplFileName("%sServiceImpl")
                            .controllerBuilder() // Controller策略
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .enableFileOverride(); // 覆盖
                })
                .execute();
    }
    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
