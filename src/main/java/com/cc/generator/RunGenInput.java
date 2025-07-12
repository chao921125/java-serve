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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RunGenInput {
	private static final Logger logger = LoggerFactory.getLogger(RunGenInput.class);

	public static void main(String[] args) {
		logger.info("======开始生成======");
		// 支持命令行参数或环境变量配置
		final String DATA_SOURCE = System.getProperty("gen.datasource", System.getenv().getOrDefault("GEN_DATASOURCE", "jdbc:mysql://localhost:3306/cc?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8"));
		final String DATA_NAME = System.getProperty("gen.username", System.getenv().getOrDefault("GEN_USERNAME", "root"));
		final String DATA_PWD = System.getProperty("gen.password", System.getenv().getOrDefault("GEN_PASSWORD", "root1234"));

		final String TABLE_NAME_PREFIX = System.getProperty("gen.tablePrefix", System.getenv().getOrDefault("GEN_TABLE_PREFIX", ""));
		final String PKG = System.getProperty("gen.pkg", System.getenv().getOrDefault("GEN_PKG", "system"));
		final String PKG_PATH = System.getProperty("gen.pkgPath", System.getenv().getOrDefault("GEN_PKG_PATH", "/src/main/java/code"));
		final String PKG_PATH_MAPPER = System.getProperty("gen.pkgPathMapper", System.getenv().getOrDefault("GEN_PKG_PATH_MAPPER", "/src/main/java/code"));

		FastAutoGenerator.create(DATA_SOURCE, DATA_NAME, DATA_PWD)
				.globalConfig((scanner, builder) -> {
					builder.enableSwagger()
							.author(scanner.apply("请输入作者：")) // 作者
							.commentDate("yyyy-mm-dd HH:MM:SS") // 时间
							.outputDir(System.getProperty("user.dir") + PKG_PATH) // 目录
							.dateType(DateType.ONLY_DATE)
							.disableOpenDir(); // 禁止打开目录
				})
				.dataSourceConfig(builder -> {
					builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
						// 兼容旧版本转换成Integer
						if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
							return DbColumnType.INTEGER;
						}
						return typeRegistry.getColumnType(metaInfo);
					});
				})
				.packageConfig(builder -> {
					builder.parent(PKG) // 设置父包，mapper路径
							.controller("controller")
							.entity("entity")
							.service("service")
							.serviceImpl("service.impl")
							.mapper("mapper")
							.xml("mapper.xml")
							.pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + PKG_PATH_MAPPER));
				})
				.strategyConfig((scanner, builder) -> {
					builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all"))) // 表
							.addTablePrefix(TABLE_NAME_PREFIX) // 过滤表前缀
							.entityBuilder() // Entity策略
							.enableTableFieldAnnotation()
							.addTableFills(new com.baomidou.mybatisplus.generator.fill.Column("create_time", com.baomidou.mybatisplus.annotation.FieldFill.INSERT))
							.addTableFills(new com.baomidou.mybatisplus.generator.fill.Column("update_time", com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE))
							.addTableFills(new com.baomidou.mybatisplus.generator.fill.Column("create_by", com.baomidou.mybatisplus.annotation.FieldFill.INSERT))
							.addTableFills(new com.baomidou.mybatisplus.generator.fill.Column("update_by", com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE))
							.enableLombok()
							.enableTableFieldAnnotation()
							.naming(NamingStrategy.underline_to_camel) // 数据库映射实体，驼峰
							.columnNaming(NamingStrategy.underline_to_camel) // 字段映射，驼峰
							.enableFileOverride() // 覆盖已经生成的
							.mapperBuilder() // Mapper策略
							.superClass(BaseMapper.class)
							.formatMapperFileName("%sMapper")
							.formatXmlFileName("%sMapper")
							.enableFileOverride() // 覆盖
							.serviceBuilder() // Service策略
							.enableFileOverride() // 覆盖
							.formatServiceFileName("%sService") // %s匹配表名，接口文件名
							.formatServiceImplFileName("%sServiceImpl")
							.controllerBuilder() // Controller策略
							.formatFileName("%sController")
							.enableRestStyle()
							.enableHyphenStyle()
							.enableFileOverride(); // 覆盖
				})
				.injectionConfig(builder -> {
//                    builder.build();
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
