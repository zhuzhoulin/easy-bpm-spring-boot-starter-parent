package com.pig.easy.bpm.generator.vo.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/3/3 15:34
 */
@Data
@ToString
public class TableFieldSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    List<ColumnSaveOrUpdateVO> columnList;
}
