package com.pig.easy.bpm.common.generator.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : zhoulin.zhu
 * @date : 2021/3/2 21:20
 */
@Data
@ToString
public class DictDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String label;

    private Object value;

}
