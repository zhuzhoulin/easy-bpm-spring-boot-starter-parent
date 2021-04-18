package com.pig.easy.bpm.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/2 17:28
 */
@Mapper
public interface  CheckTableMapper {

    /**
     * @param tableSchema
     * @param tableName
     * @return
     */
    Integer checkTableExistsWithSchema(@Param("tableSchema")String tableSchema, @Param("tableName")String tableName);

    @Select("select * from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    List<Map> listTable();

    @Select("select * from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME=#{tableName}")
    List<Map> listTableColumn(String tableName);
}
