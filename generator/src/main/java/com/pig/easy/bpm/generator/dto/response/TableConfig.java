package com.pig.easy.bpm.generator.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/5 16:53
 */
@Data
@ToString
public class TableConfig extends TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表主键ID 字段名
     */
    private String keyColumn;
    /**
     * 表主键ID 属性名
     */
    private String keyProperty;
    /**
     * 表主键ID 属性类型
     */
    private Class<?> keyType;

    private boolean underCamel = true;

    /**
     * 表字段信息列表
     */
    private List<com.pig.easy.bpm.generator.dto.response.TableFieldInfo> fields;
}
