package com.pig.easy.bpm.auth.dto.response;

import com.pig.easy.bpm.common.generator.dto.response.BaseResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/27 16:16
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class TreeItemDTO extends BaseResponseDTO {

    private static final long serialVersionUID = -3067098296529145203L;

    private Object value;

    private String label;

}
