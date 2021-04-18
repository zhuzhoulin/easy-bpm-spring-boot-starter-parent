package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/14 10:13
 */
@Data
@ToString
public class ListDTO implements Serializable {

    private static final long serialVersionUID = -8668222478459197333L;

    private Long id;

    private String code;

    private String name;

}
