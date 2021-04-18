package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/29 10:45
 */
@Data
@ToString
public class FormDataTemplateDTO implements Serializable {

    private static final long serialVersionUID = 190427095640345414L;

    private String type;

    private String icon;

    private String label;

    private Object options;

    private String model;

    private String key;

    private String rule;


}
