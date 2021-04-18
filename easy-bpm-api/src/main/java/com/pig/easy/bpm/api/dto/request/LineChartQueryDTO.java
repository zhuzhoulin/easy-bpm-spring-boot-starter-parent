package com.pig.easy.bpm.api.dto.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/8/10 16:07
 */
@Data
@ToString
public class LineChartQueryDTO implements Serializable {

    private static final long serialVersionUID = -8006234461808451545L;

    private String tenantId;

    private Long userId;

    private Integer applyStatus;
}
