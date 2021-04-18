package com.pig.easy.bpm.generator.generator;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/24 17:20
 */
public abstract class AbstractCustomDbQuery implements CustomDbQuery {

    @Override
    public String notNull() {
        return null;
    }

    @Override
    public String collation() {
        return null;
    }

    @Override
    public String privileges() {
        return null;
    }

    @Override
    public String extra() {
        return null;
    }

    @Override
    public String defaultValue() {
        return null;
    }

    @Override
    public String queryCreateTableSql() {
        return null;
    }
}
