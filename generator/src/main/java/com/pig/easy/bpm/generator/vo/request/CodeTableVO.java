package com.pig.easy.bpm.generator.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/2/24 10:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CodeTableVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dbId;

    private String includeTables;

    private Boolean queryTableFiled;
}
