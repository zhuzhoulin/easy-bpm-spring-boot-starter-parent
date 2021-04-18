package com.pig.easy.bpm.api.dto.response;

import com.pig.easy.bpm.auth.dto.response.FileTemplateDTO;
import com.pig.easy.bpm.auth.dto.response.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/30 10:10
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ApproveTaskDTO extends BaseResponseDTO {

    private static final long serialVersionUID = -5873527097816097929L;

    private UserDTO applyUserInfo;

    private ApplyDTO applyInfo;

    private Map<String,Object> businessData;

    private UserTaskDTO userTaskInfo;

    private List<HistoryDTO> historyList;

    private NodeDTO nodeInfo;

    private DynamicFormDataDTO dynamicFormData;

    private List<NodeUserDTO> selectNodeUsers;

    private List<FileTemplateDTO> fileTempleteList;

}
