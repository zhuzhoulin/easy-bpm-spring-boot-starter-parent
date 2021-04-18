package com.pig.easy.bpm.api.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/5/21 10:59
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ApplyReqDTO extends BaseRequestDTO {

    private static final long serialVersionUID = -2422502808166747890L;

    private String tenantId;

    private String system;

    private Long applyId;

    private String processKey;

    private String formKey;

    private Map<String,Object> data;

    /**
     *  流程发起人
     */
    private Long startUserId;

    /**
     *  流程制单人，制单人可以帮 其他人发起流程，区分
     */
    private Long createUserId;

    private String createrRealName;

}


