package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/4 14:11
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ProcessDiagramDTO extends BaseResponseDTO {

    private static final long serialVersionUID = 491311304700721029L;

    private String processXml;

    private Map<String,DiagramNodeDTO> nodeMap;
}
