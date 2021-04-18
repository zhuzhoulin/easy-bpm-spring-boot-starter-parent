package com.pig.easy.bpm.generator.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.IDbQuery;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.H2Query;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.pig.easy.bpm.generator.generator.CustomDbQuery;
import com.pig.easy.bpm.generator.generator.CustomMySqlQuery;
import com.pig.easy.bpm.generator.generator.MySqlCustomDbQuery;

import java.sql.*;
import java.util.*;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/23 9:59
 * @see com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder
 */
public class DbUtils {

    public static Connection getConn(String driverName, String url, String username, String password) {
        Connection conn = null;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static boolean testConn(String driverName, String url, String username, String password) {
        return getConn(driverName, url, username, password) != null;
    }

    public static DataSourceConfig buildDataSourceConfig(String driverName, String url, String username, String password) {

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName(driverName);
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setDbType(dataSourceConfig.getDbType());

        if (dataSourceConfig.getDbType() == DbType.MYSQL) {
            dataSourceConfig.setDbQuery(new CustomMySqlQuery());
        }
        return dataSourceConfig;
    }


    /**
     * 获取所有的数据库表信息
     */
    public static List<TableInfo> getTablesInfo(DataSourceConfig dataSourceConfig, String[] includeTables, boolean queryTableFiled) {

        boolean isInclude = (null != includeTables && includeTables.length > 0);
        //SQLITE 数据库不支持注释获取
        boolean commentSupported = !dataSourceConfig.getDbType().equals(DbType.SQLITE);
        /**
         * 是否跳过视图
         */
        boolean skipView = false;
        //所有的表信息
        List<TableInfo> tableList = new ArrayList<>();

        //需要反向生成或排除的表信息
        List<TableInfo> includeTableList = new ArrayList<>();

        //不存在的表名
        Set<String> notExistTables = new HashSet<>();

        Connection connection = dataSourceConfig.getConn();
        IDbQuery dbQuery = dataSourceConfig.getDbQuery();
        try {
            String tablesSql = dbQuery.tablesSql();
            if (DbType.POSTGRE_SQL == dbQuery.dbType()) {
                String schema = dataSourceConfig.getSchemaName();
                if (schema == null) {
                    //pg 默认 schema=public
                    schema = "public";
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            }
            if (DbType.DB2 == dbQuery.dbType()) {
                String schema = dataSourceConfig.getSchemaName();
                if (schema == null) {
                    //db2 默认 schema=current schema
                    schema = "current schema";
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            }
            //oracle数据库表太多，出现最大游标错误
            else if (DbType.ORACLE == dbQuery.dbType()) {
                String schema = dataSourceConfig.getSchemaName();
                //oracle 默认 schema=username
                if (schema == null) {
                    schema = dataSourceConfig.getUsername().toUpperCase();
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
                if (isInclude) {
                    StringBuilder sb = new StringBuilder(tablesSql);
                    sb.append(" AND ").append(dbQuery.tableName()).append(" IN (");
                    Arrays.stream(includeTables).forEach(tbname -> sb.append(StringPool.SINGLE_QUOTE).append(tbname.toUpperCase()).append("',"));
                    sb.replace(sb.length() - 1, sb.length(), StringPool.RIGHT_BRACKET);
                    tablesSql = sb.toString();
                }
            }
            TableInfo tableInfo;
            try (PreparedStatement preparedStatement = connection.prepareStatement(tablesSql);
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String tableName = results.getString(dbQuery.tableName());
                    if (StringUtils.isNotEmpty(tableName)) {
                        tableInfo = new TableInfo();
                        tableInfo.setName(tableName);

                        if (commentSupported) {
                            String tableComment = results.getString(dbQuery.tableComment());
                            if (skipView && "VIEW".equals(tableComment)) {
                                // 跳过视图
                                continue;
                            }
                            tableInfo.setComment(tableComment);
                        }

                        if (isInclude) {
                            for (String includeTable : includeTables) {
                                // 忽略大小写等于 或 正则 true
                                if (tableNameMatches(includeTable, tableName)) {
                                    includeTableList.add(tableInfo);
                                } else {
                                    notExistTables.add(includeTable);
                                }
                            }
                        }
                        tableList.add(tableInfo);
                    } else {
                        System.err.println("当前数据库为空！！！");
                    }
                }
            }
            // 将已经存在的表移除，获取配置中数据库不存在的表
            for (TableInfo tabInfo : tableList) {
                notExistTables.remove(tabInfo.getName());
            }
            if (notExistTables.size() > 0) {
                throw new RuntimeException("表 " + notExistTables + " 在数据库中不存在！！！");
            }

            if (!isInclude) {
                includeTableList = tableList;
            }
            if (queryTableFiled) {
                // 性能优化，只处理需执行表字段 github issues/219
                includeTableList.forEach(ti -> convertTableFields(ti, dataSourceConfig));
            } else {
                List<TableField> fieldList = new ArrayList<>();
                TableField tableField = new TableField();
                tableField.setName("test_field");
                tableField.setPropertyName("testField");
                tableField.setColumnType(DbColumnType.STRING);
                fieldList.add(tableField);
                includeTableList.forEach(ti -> {
                    ti.setFields(fieldList);
                    ti.setCommonFields(fieldList);
                    ti.setFieldNames("");
                });
            }

            // 去掉无用信息
            includeTableList.forEach(ti -> {
                if (StringUtils.isEmpty(ti.getEntityName())) {
                    ti.setEntityName("com");
                }
                if (StringUtils.isEmpty(ti.getControllerName())) {
                    ti.setControllerName("com");
                }
                if (StringUtils.isEmpty(ti.getMapperName())) {
                    ti.setMapperName("com");
                }
                if (StringUtils.isEmpty(ti.getServiceImplName())) {
                    ti.setServiceImplName("com");
                }
                if (StringUtils.isEmpty(ti.getServiceName())) {
                    ti.setServiceName("com");
                }
                if (StringUtils.isEmpty(ti.getXmlName())) {
                    ti.setXmlName("com");
                }

            });


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return includeTableList;
    }


    public static Map<String, Object> executeQuery(Connection connection, String sql) {

        if (connection == null) {
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet results = preparedStatement.executeQuery()) {
            return convertMap(results);
        } catch (Exception execption) {
            execption.printStackTrace();
        }

        return null;
    }

    /**
     * 将字段信息与表信息关联
     *
     * @param tableInfo        表信息
     * @param dataSourceConfig 数据库策略
     * @return ignore
     */
    public static TableInfo convertTableFields(TableInfo tableInfo, DataSourceConfig dataSourceConfig) {
        boolean haveId = false;
        Connection connection = dataSourceConfig.getConn();
        IDbQuery dbQuery = dataSourceConfig.getDbQuery();
        //SQLITE 数据库不支持注释获取
        boolean commentSupported = !dataSourceConfig.getDbType().equals(DbType.SQLITE);
        /**
         * 是否跳过视图
         */
        boolean skipView = false;
        List<TableField> fieldList = new ArrayList<>();
        List<TableField> commonFieldList = new ArrayList<>();
        DbType dbType = dbQuery.dbType();
        String tableName = tableInfo.getName();
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            Set<String> h2PkColumns = new HashSet<>();
            if (DbType.POSTGRE_SQL == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.DB2 == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.ORACLE == dbType) {
                tableName = tableName.toUpperCase();
                tableFieldsSql = String.format(tableFieldsSql.replace("#schema", dataSourceConfig.getSchemaName()), tableName);
            } else if (DbType.H2 == dbType) {
                tableName = tableName.toUpperCase();
                try (PreparedStatement pkQueryStmt = connection.prepareStatement(String.format(H2Query.PK_QUERY_SQL, tableName));
                     ResultSet pkResults = pkQueryStmt.executeQuery()) {
                    while (pkResults.next()) {
                        String primaryKey = pkResults.getString(dbQuery.fieldKey());
                        if (Boolean.valueOf(primaryKey)) {
                            h2PkColumns.add(pkResults.getString(dbQuery.fieldName()));
                        }
                    }
                }
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            } else {
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            }
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
                    ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    TableField field = new TableField();
                    String columnName = results.getString(dbQuery.fieldName());
                    // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
                    boolean isId;
                    if (DbType.H2 == dbType) {
                        isId = h2PkColumns.contains(columnName);
                    } else {
                        String key = results.getString(dbQuery.fieldKey());
                        if (DbType.DB2 == dbType || DbType.SQLITE == dbType) {
                            isId = StringUtils.isNotEmpty(key) && "1".equals(key);
                        } else {
                            isId = StringUtils.isNotEmpty(key) && "PRI".equals(key.toUpperCase());
                        }
                    }

                    // 处理ID
                    if (isId && !haveId) {
                        field.setKeyFlag(true);
                        if (DbType.H2 == dbType || DbType.SQLITE == dbType || dbQuery.isKeyIdentity(results)) {
                            field.setKeyIdentityFlag(true);
                        }
                        haveId = true;
                    } else {
                        field.setKeyFlag(false);
                    }
                    // 自定义字段查询
                    String[] fcs = dbQuery.fieldCustom();
                    if (null != fcs) {
                        Map<String, Object> customMap = new HashMap<>(fcs.length);
                        for (String fc : fcs) {
                            customMap.put(fc, results.getObject(fc));
                        }
                        field.setCustomMap(customMap);
                    }
                    // 处理其它信息
                    field.setName(columnName);
                    field.setType(results.getString(dbQuery.fieldType()));

                    StrategyConfig strategyConfig = new StrategyConfig();
                    field.setPropertyName(strategyConfig, processName(field.getName(), strategyConfig));
                    GlobalConfig globalConfig = new GlobalConfig();
                    field.setColumnType(dataSourceConfig.getTypeConvert().processTypeConvert(globalConfig, field.getType()));
                    if (commentSupported) {
                        field.setComment(results.getString(dbQuery.fieldComment()));
                    }

                    fieldList.add(field);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL Exception：" + e.getMessage());
        }
        tableInfo.setFields(fieldList);
        tableInfo.setCommonFields(commonFieldList);
        return tableInfo;
    }

    public static CustomDbQuery getCustomDbQuery(DbType dbType) {

        CustomDbQuery customDbQuery = null;
        switch (dbType) {
            default:
                // 默认 MYSQL
                customDbQuery = new MySqlCustomDbQuery();
                break;
        }

        return customDbQuery;
    }

    /**
     * 处理字段名称
     *
     * @return 根据策略返回处理后的名称
     */
    private static String processName(String name, StrategyConfig strategyConfig) {
        return processName(name, strategyConfig.getNaming(), strategyConfig.getFieldPrefix());
    }

    /**
     * 处理表/字段名称
     *
     * @param name     ignore
     * @param strategy ignore
     * @param prefix   ignore
     * @return 根据策略返回处理后的名称
     */
    private static String processName(String name, NamingStrategy strategy, String[] prefix) {
        boolean removePrefix = false;
        if (prefix != null && prefix.length != 0) {
            removePrefix = true;
        }
        String propertyName;
        if (removePrefix) {
            if (strategy == NamingStrategy.underline_to_camel) {
                // 删除前缀、下划线转驼峰
                propertyName = NamingStrategy.removePrefixAndCamel(name, prefix);
            } else {
                // 删除前缀
                propertyName = NamingStrategy.removePrefix(name, prefix);
            }
        } else if (strategy == NamingStrategy.underline_to_camel) {
            // 下划线转驼峰
            propertyName = NamingStrategy.underlineToCamel(name);
        } else {
            // 不处理
            propertyName = name;
        }
        return propertyName;
    }

    public static List<Map<String, Object>> convertList(ResultSet rs) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        JSON.toJSONStringWithDateFormat(list,JSON.DEFFAULT_DATE_FORMAT);
        return list;
    }


    public static Map<String, Object> convertMap(ResultSet rs) {
        Map<String, Object> map = new TreeMap<String, Object>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    map.put(md.getColumnName(i), rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return map;
        }
    }

    /**
     * 表名匹配
     *
     * @param setTableName 设置表名
     * @param dbTableName  数据库表单
     * @return ignore
     */
    private static boolean tableNameMatches(String setTableName, String dbTableName) {
        return setTableName.equalsIgnoreCase(dbTableName)
                || StringUtils.matches(setTableName, dbTableName);
    }
}
