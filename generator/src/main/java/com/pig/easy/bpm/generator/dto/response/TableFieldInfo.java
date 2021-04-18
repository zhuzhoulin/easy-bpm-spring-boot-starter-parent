package com.pig.easy.bpm.generator.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/5 16:58
 */
@Data
@ToString
public class TableFieldInfo extends ColumnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段名

     */
    private  String column;
    /**
     * 属性名
     */
    private  String property;
    /**
     * 属性类型
     */
    private  String propertyType;

    private  Class<?> propertyTypeClass;

}
