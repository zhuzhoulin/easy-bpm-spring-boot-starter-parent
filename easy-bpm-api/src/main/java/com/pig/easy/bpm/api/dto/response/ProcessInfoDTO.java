package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/6 12:56
 */
@Data
@ToString
public class ProcessInfoDTO extends ProcessDTO {

    private static final long serialVersionUID = 6963706717223778411L;

    private String definitionId;

    private String processXml;

    private String applyTitleRule;

    private Integer autoCompleteFirstNode;

    private LocalDateTime applyDueDate;

}
