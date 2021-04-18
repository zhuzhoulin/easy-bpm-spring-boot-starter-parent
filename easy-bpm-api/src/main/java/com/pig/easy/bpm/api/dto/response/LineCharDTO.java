package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/8/10 16:05
 */
@Data
@ToString
public class LineCharDTO implements Serializable {

    private static final long serialVersionUID = 4179888146925441206L;

    private String applyMonth;

    private Integer processId;

    private String processName;

    private Integer num;
}
