package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/4 12:04
 */
@Data
@ToString
public class BatchCompleteTaskVO implements Serializable {

    private static final long serialVersionUID = 2685964739880458348L;

    private Map<String,Object> businessData;

    @NotNull
    private List<Long> taskIds;

    @NotNull
    private String approveActionCode;

    @NotNull
    private String approveOpinion;
}
