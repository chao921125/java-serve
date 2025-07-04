package com.cc.generator;

import java.text.SimpleDateFormat;
import java.util.*;

public class RunGen {
	public static void main(String[] args) throws Exception {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("请输入包名（如com.cc.server）：");
			String packageName = scanner.nextLine().trim();

			System.out.print("请输入作者名：");
			String author = scanner.nextLine().trim();

			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

			System.out.println("请输入数据库连接信息：");
			System.out.print("JDBC URL（如jdbc:mysql://localhost:3306/serve?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8）：");
			String jdbcUrl = scanner.nextLine().trim();
			System.out.print("用户名：");
			String dbUser = scanner.nextLine().trim();
			System.out.print("密码：");
			String dbPwd = scanner.nextLine().trim();

			DBUtil dbUtil = new DBUtil(jdbcUrl, dbUser, dbPwd);

			System.out.print("请输入表名（多个用逗号分隔，或输入all生成全部表）：");
			String tableInput = scanner.nextLine().trim();
			List<String> tableList;
			if ("all".equalsIgnoreCase(tableInput)) {
				tableList = dbUtil.getAllTableNames();
			} else {
				tableList = Arrays.asList(tableInput.split(","));
			}

			for (String tableName : tableList) {
				tableName = tableName.trim();
				if (tableName.isEmpty()) continue;
				// 实体类名，首字母大写+驼峰
				String className = toCamelCase(tableName, true);
				String voClass = className + "VO";
				List<DBUtil.ColumnInfo> columns = dbUtil.getTableColumns(tableName);
				StringBuilder fields = new StringBuilder();
				for (DBUtil.ColumnInfo col : columns) {
					String javaType = sqlTypeToJavaType(col.type);
					String fieldName = toCamelCase(col.name, false);
					if (col.isPk) {
						fields.append("    @TableId\n");
					} else {
						fields.append("    @TableField(\"").append(col.name).append("\")\n");
					}
					fields.append("    private ").append(javaType).append(" ").append(fieldName).append(";");
					if (col.remark != null && !col.remark.isEmpty()) {
						fields.append(" // ").append(col.remark);
					}
					fields.append("\n");
				}
				String entityCode = CodeTemplate.entity(packageName, className, fields.toString(), tableName, author, date);
				String mapperCode = CodeTemplate.mapper(packageName, className);
				String serviceCode = CodeTemplate.service(packageName, className);
				String serviceImplCode = CodeTemplate.serviceImpl(packageName, className);
				String controllerCode = CodeTemplate.controller(packageName, className, voClass, author, date);
				String voCode = CodeTemplate.vo(packageName, voClass, fields.toString(), author, date);
				String converterCode = CodeTemplate.converter(packageName, className, voClass);
				String mapperXmlCode = CodeTemplate.mapperXml(packageName, className, tableName);

				String baseDir = "src/main/java/" + packageName.replace('.', '/');
				String resourceDir = "src/main/resources/mapper/" + className.toLowerCase();

				FileUtil.writeToFile(baseDir + "/entity/" + className + ".java", entityCode);
				FileUtil.writeToFile(baseDir + "/mapper/" + className + "Mapper.java", mapperCode);
				FileUtil.writeToFile(baseDir + "/service/" + className + "Service.java", serviceCode);
				FileUtil.writeToFile(baseDir + "/service/impl/" + className + "ServiceImpl.java", serviceImplCode);
				FileUtil.writeToFile(baseDir + "/controller/" + className + "Controller.java", controllerCode);
				FileUtil.writeToFile(baseDir + "/vo/" + voClass + ".java", voCode);
				FileUtil.writeToFile(baseDir + "/vo/" + className + "Converter.java", converterCode);
				FileUtil.writeToFile(resourceDir + "/" + className + "Mapper.xml", mapperXmlCode);

				System.out.println("表 " + tableName + " 代码生成完成！");
			}
			System.out.println("全部代码生成完成！");
		}
	}

	// 下划线转驼峰
	private static String toCamelCase(String s, boolean upperFirst) {
		StringBuilder sb = new StringBuilder();
		boolean upper = upperFirst;
		for (char c : s.toCharArray()) {
			if (c == '_' || c == '-') {
				upper = true;
			} else if (upper) {
				sb.append(Character.toUpperCase(c));
				upper = false;
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}

	// 简单SQL类型到Java类型映射
	private static String sqlTypeToJavaType(String sqlType) {
		sqlType = sqlType.toLowerCase();
		if (sqlType.contains("char") || sqlType.contains("text")) return "String";
		if (sqlType.contains("bigint")) return "Long";
		if (sqlType.contains("int")) return "Integer";
		if (sqlType.contains("double")) return "Double";
		if (sqlType.contains("float")) return "Float";
		if (sqlType.contains("decimal") || sqlType.contains("numeric")) return "java.math.BigDecimal";
		if (sqlType.contains("date") || sqlType.contains("time")) return "java.util.Date";
		if (sqlType.contains("bit") || sqlType.contains("boolean")) return "Boolean";
		return "String";
	}
}
