package com.pig.easy.bpm.api.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/1 15:59
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteTaskDTO extends BaseRequestDTO implements Serializable {

    private static final long serialVersionUID = -770108451041652581L;

    private String tenantId;

    private String system;

    private Long taskId;

    private Long approveId;

    private String approveName;

    private List<Long> taskIds;

    private Map<String,Object> businessData;

    private String approveOpinion;

    private String approveActionCode;

}
