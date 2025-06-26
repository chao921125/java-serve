package com.cc.generator;

import java.sql.*;
import java.util.*;

public class DBUtil {
    private final String url;
    private final String user;
    private final String password;

    public DBUtil(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public List<String> getAllTableNames() throws Exception {
        List<String> tables = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(conn.getCatalog(), null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        }
        return tables;
    }

    public List<ColumnInfo> getTableColumns(String tableName) throws Exception {
        List<ColumnInfo> columns = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getColumns(conn.getCatalog(), null, tableName, "%");
            Set<String> pkSet = new HashSet<>();
            ResultSet pkRs = metaData.getPrimaryKeys(conn.getCatalog(), null, tableName);
            while (pkRs.next()) {
                pkSet.add(pkRs.getString("COLUMN_NAME"));
            }
            while (rs.next()) {
                String name = rs.getString("COLUMN_NAME");
                String type = rs.getString("TYPE_NAME");
                String remark = rs.getString("REMARKS");
                boolean isPk = pkSet.contains(name);
                columns.add(new ColumnInfo(name, type, remark, isPk));
            }
        }
        return columns;
    }

    public static class ColumnInfo {
        public String name;
        public String type;
        public String remark;
        public boolean isPk;

        public ColumnInfo(String name, String type, String remark, boolean isPk) {
            this.name = name;
            this.type = type;
            this.remark = remark;
            this.isPk = isPk;
        }
    }
} 