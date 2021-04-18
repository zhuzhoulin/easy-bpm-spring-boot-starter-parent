package com.pig.easy.bpm.api.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.Data;
import lombok.ToString;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/28 14:55
 */
@Data
@ToString
public class FormAddDTO extends BaseRequestDTO {

    private static final long serialVersionUID = 2471317839471263784L;

    private Long formId;

    private String formKey;

    private String formName;

    private String tenantId;

    private Integer sort;

    private String formData;

    private String remarks;

    private Integer validState;

    private Long operatorId;

    private String operatorName;
}
