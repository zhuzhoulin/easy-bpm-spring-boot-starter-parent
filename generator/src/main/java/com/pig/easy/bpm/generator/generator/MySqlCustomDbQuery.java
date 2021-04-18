package com.pig.easy.bpm.generator.generator;

import com.baomidou.mybatisplus.annotation.DbType;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/24 17:22
 */
public class MySqlCustomDbQuery extends AbstractCustomDbQuery {

    @Override
    public String notNull() {
        return "NULL";
    }

    @Override
    public CustomDbQuery getCustomDbQuery(DbType dbType) {
        return new MySqlCustomDbQuery();
    }

    @Override
    public String collation() {
        return "COLLATION";
    }

    @Override
    public String privileges() {
        return "PRIVILEGES";
    }

    @Override
    public String extra() {
        return "EXTRA";
    }

    @Override
    public String queryCreateTableSql() {
        return "SHOW CREATE TABLE %s";
    }

    @Override
    public String defaultValue() {
        return "DEFAULT";
    }
}
