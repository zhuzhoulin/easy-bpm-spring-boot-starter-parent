package com.pig.easy.bpm.auth.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.Data;
import lombok.ToString;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/26 17:47
 */
@Data
@ToString
public class DictUpdateDTO extends BaseRequestDTO {

    private static final long serialVersionUID = 674896436965449339L;

    private Long dictId;

    private String dictCode;

    private String dictName;

    private Integer dictTree;

    private String tenantId;

    private String remark;

    private Integer validState;

}
