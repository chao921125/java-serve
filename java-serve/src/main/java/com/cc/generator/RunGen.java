package com.cc.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RunGen {
    private static final Logger logger = LoggerFactory.getLogger(RunGen.class);

    public static void main(String[] args) {
        logger.info("======开始生成======");
        final String DATA_SOURCE = "jdbc:mysql://localhost:3306/serve?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        final String DATA_NAME = "root";
//        final String DATA_PWD = "Admin123.";
//        final String DATA_PWD = "root123456";
        final String DATA_PWD = "root1234";

        final String TABLE_NAME = "all";
        final String TABLE_NAME_PREFIX = "";

        final String PKG = "";
        final String PKG_PATH = "/serve-server/src/main/java/code";
        final String PKG_PATH_MAPPER = "/serve-server/src/main/java/code";
        final String PKG_PATH_VO = "/serve-server/src/main/java/code/vo";

        FastAutoGenerator.create(DATA_SOURCE, DATA_NAME, DATA_PWD)
//                全局配置
                .globalConfig(builder -> {
                    builder.enableSwagger()
                            .author("cc") // 作者
                            .commentDate("yyyy-MM-dd HH:mm:ss") // 时间
                            .outputDir(System.getProperty("user.dir") + PKG_PATH) // 目录
                            .dateType(DateType.ONLY_DATE)
                            .disableOpenDir() // 禁止打开目录
                            .build();
                })
//                数据库配置
                .dataSourceConfig(builder -> {
                    builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                        // 兼容旧版本转换成Integer
                        if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                            return DbColumnType.INTEGER;
                        }
                        return typeRegistry.getColumnType(metaInfo);
                    }).build();
                })
//                包配置
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
//                策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(getTables(TABLE_NAME)) // 表
                            .addTablePrefix(TABLE_NAME_PREFIX) // 过滤表前缀
                            .entityBuilder() // Entity 策略
                            .enableTableFieldAnnotation()
                            .naming(NamingStrategy.underline_to_camel) // 数据库映射实体，驼峰
                            .columnNaming(NamingStrategy.underline_to_camel) // 字段映射，驼峰
                            .enableFileOverride() // Entity 覆盖
                            .controllerBuilder() // Controller 策略
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .enableFileOverride() // Controller 覆盖
                            .serviceBuilder() // Service 策略
                            .formatServiceFileName("%sService") // %s匹配表名，接口文件名
                            .formatServiceImplFileName("%sServiceImpl")
                            .enableFileOverride() // Service 覆盖
                            .mapperBuilder() // Mapper 策略
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper")
                            .enableFileOverride() // Mapper 覆盖
                            .build();
                })
//                注入配置
                .injectionConfig(builder -> {
                    Map<String, String> customFile = new HashMap<>();
                    // VO
                    customFile.put("VO.java", "/templates/entityVO.java.ftl");

                    builder.beforeOutputFile((tableInfo, objectMap) -> {
                                // 自定义文件输出逻辑
                                objectMap.put("pathInfo", PKG_PATH_VO);
                            })
                            .customFile(customFile)
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用自定义的模板引擎
                .execute();
        logger.info("======生成代码结束======");
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
