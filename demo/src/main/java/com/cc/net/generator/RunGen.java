package com.cc.net.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;

public class RunGen {

    private static final String AUTHOR = "cc";
    private static final String COMMENT_DATE = "yyyy-mm-dd HH:MM:SS";
    private static final String PKG = "com.cc.net";
    private static final String ROOT_PATH = System.getProperty("user.dir");
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root123456";

    public static void main(String[] args) {
        // java
        String pkgPath = ROOT_PATH + "/src/main/java";
        // xml
        String xmlPath = ROOT_PATH + "/src/main/resources/mapper";
        // 数据源
        DataSourceConfig dsc = new DataSourceConfig.Builder(URL, USER_NAME, PASSWORD).build();
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator(dsc);
    }
}
