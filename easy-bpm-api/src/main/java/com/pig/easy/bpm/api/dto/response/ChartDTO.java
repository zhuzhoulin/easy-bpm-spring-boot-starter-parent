package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/8/10 16:24
 */
@Data
@ToString
public class ChartDTO implements Serializable {

    private static final long serialVersionUID = -1913418516878670882L;

    private List<String> legendDataList;

    private String titleText;

    private String subtext;

    private List<String> xAxisDataList;

    private Integer yAxisMax;

    private List<Object> dataList;
}
