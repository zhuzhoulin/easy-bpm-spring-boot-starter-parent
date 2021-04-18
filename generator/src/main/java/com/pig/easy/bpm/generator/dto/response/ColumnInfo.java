package com.pig.easy.bpm.generator.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/5 11:28
 */
@Data
@ToString
public class ColumnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表限定符的 字段值永远是def
     */
    private String tableCataLog;

    /**
     * 表格所属的库。
     */
    private String tableSchema;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段标识。
     */
    private Long ordinalPosition;

    /**
     * 字段默认值。
     */
    private String columnDefault;

    /**
     * 字段是否可以是NULL。该列记录的值是YES或者NO。
     */
    private String isNullable;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 以字符为单位的最大长度。
     */
    private Long characterMaximumLength;

    /**
     * 以字节为单位的最大长度
     */
    private Long characterOctetLength;
    /**
     * 数字精度。
     */
    private Long numericPrecision;
    /**
     * 小数位数。
     */
    private Long numericScale;
    /**
     * datetime类型和SQL-92interval类型数据库的子类型代码。
     */
    private Long datetimePrecision;
    /**
     * 字段字符集名称
     */
    private String characterSetName;
    /**
     * 字符集排序规则。
     */
    private String collationName;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 索引类型。
     */
    private String columnKey;
    /**
     * 其他信息。 比如主键的auto_increment。
     */
    private String extra;
    /**
     * 权限
     */
    private String privileges;
    /**
     * 字段注释
     */
    private String columnComment;
    /**
     * 组合字段的公式。
     */
    private String generationExpression;

}


