package com.pig.easy.bpm.api.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/4 14:59
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class NodeUserQueryDTO extends BaseRequestDTO {

    private static final long serialVersionUID = 819468737031725612L;

    private Long nodeUserId;

    /**
     * 申请编号
     */
    private Long applyId;

    /**
     * 流程实例编号
     */
    private String procInstId;

    /**
     * 流程编号
     */
    private Long processId;

    /**
     * 流程KEY
     */
    private String processKey;

    /**
     * 节点编号
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 流程定义编号
     */
    private String definitionId;

    private String parentNodeId;

    /**
     * 父节点名称
     */
    private String parentNodeName;

    /**
     * 租户编号
     */
    private String tenantId;

    private Integer validState;

}
