package com.pig.easy.bpm.web.vo.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/3 9:46
 */

@Data
@ToString
public class CompleteTaskVO implements Serializable {

    private static final long serialVersionUID = 4952132101569978212L;

    private Map<String,Object> businessData;

    @NotNull
    private Long taskId;

    @NotNull
    private String approveActionCode;

    @NotNull
    private String approveOpinion;

}
