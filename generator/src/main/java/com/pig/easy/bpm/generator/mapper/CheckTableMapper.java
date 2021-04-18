package com.pig.easy.bpm.generator.mapper;

import com.pig.easy.bpm.generator.dto.response.ColumnInfo;
import com.pig.easy.bpm.generator.dto.response.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/2 17:28
 */
@Mapper
public interface CheckTableMapper {

    @Select("select * from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    List<TableInfo> listTable();

    @Select("select * from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME=#{tableName}")
    List<ColumnInfo> listTableColumn(String tableName);

    @Select("select * from information_schema.TABLES where TABLE_SCHEMA=(select database()) and TABLE_NAME=#{tableName}")
    TableInfo getTableInfo(String tableName);
}
