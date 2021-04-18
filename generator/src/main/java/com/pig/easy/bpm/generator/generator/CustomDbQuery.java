package com.pig.easy.bpm.generator.generator;

import com.baomidou.mybatisplus.annotation.DbType;

/**
 * todo:  预定义 customMap 转换字段
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/24 17:15
 */
public interface CustomDbQuery {

    /** 是否为空 */
    String notNull();

    /** 编码 */
    String collation();

    /** 权限 */
    String privileges();

    /** 额外参数 */
    String extra();

    /** 默认值 */
    String defaultValue();

    CustomDbQuery getCustomDbQuery(DbType dbType);

    String queryCreateTableSql();

}
