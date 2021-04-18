package com.pig.easy.bpm.generator.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/5 11:11
 */
@Data
@ToString
public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据表登记目录
     */
    private String tableCatalog;

    /**
     * 数据表登记目录
     */
    private String tableSchema;

    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表类型[system view|base table]
     */
    private String tableType;

    /**
     * 数据库引擎 MyISAM|CSV|InnoDB
     */
    private String engine;

    /**
     * 版本，默认值10
     */
    private String version;


    /**
     * 行格式[Compact|Dynamic|Fixed]
     */
    private String rowFormat;

    /**
     * 表里所存多少行数据
     */
    private Long tableRows;

    /**
     * 平均行长度
     */
    private Long avgRowLength;

    /**
     * 数据长度
     */
    private Long dataLength;

    /**
     * 数据长度
     */
    private Long maxDataLength;

    /**
     * 索引长度
     */
    private Long indexLength;

    /**
     * 空间碎片
     */
    private Long dataFree;

    /**
     * 空间碎片
     */
    private Long autoIncrement;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 表的更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 表的检查时间
     */
    private LocalDateTime checkTime;

    /**
     * 表的字符校验编码集
     */
    private String tableCollation;

    /**
     * 校验和
     */
    private Long checksum;

    /**
     * 创建选项
     */
    private String createOptions;

    /**
     * 表的注释、备注
     */
    private String tableComment;

}
