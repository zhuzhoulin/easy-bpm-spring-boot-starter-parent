package com.pig.easy.bpm.api.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.Data;
import lombok.ToString;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/18 14:31
 */
@Data
@ToString
public class ProcessPublishDTO extends BaseRequestDTO {

    private static final long serialVersionUID = -5250215826433777763L;

    private String processKey;

    private String tenantId;

    private Long processDetailId;

    private String processXml;

    private Long operatorId;

    private String operatorName;
}
