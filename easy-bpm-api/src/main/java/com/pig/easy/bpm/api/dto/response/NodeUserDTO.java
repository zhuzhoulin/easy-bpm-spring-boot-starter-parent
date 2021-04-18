package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/4 14:56
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class NodeUserDTO extends BaseResponseDTO {

    private static final long serialVersionUID = -1620102124126189236L;

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

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 节点分配人员工号 多个人以 , 区分
     */
    private String assigneeUserIdList;

    /**
     * 节点分配人员姓名 多个人以 , 区分
     */
    private String assigneeUserNameList;

    /**
     * 当处理策略为 节点找不到人时 跳过则为 1，否则为 0
     */
    private Integer skip;

    /**
     * 当处理策略为 节点找不到人时 管理员处理则为 1，否则为 0
     */
    private Integer defaultSetAdmin;

    /**
     * 当处理策略为 节点找不到人时 抛出异常处理则为 1，否则为 0
     */
    private Integer error;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 状态 1 有效 0 失效
     */
    private Integer validState;

    /**
     * 操作人工号
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

}
