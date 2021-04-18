package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/22 11:26
 */
@Data
@ToString
public class FlowUserTaskDTO implements Serializable {

    private static final long serialVersionUID = -1902087290704492999L;

    private String parentNodeId;

    private String parentNodeName;

    private String nodeId;

    private String nodeName;

    private Integer findUserType;

    private String roleGroupCode;

    private String roleGroupName;

    private String roleCode;

    /* 如果角色组对应多个角色，则取第一个 */
    private String roleName;

    private List<Long> owners;

    private List<String> ownerNames;

    private List<String> ownerUserIds;

    private Integer priority;

    private String formKey;

    /**
     * 节点处理策略 skip: 执行人为空跳过,admin: 为空时管理员处理,error:为空时报错
     */
    private String handlerStrategy;

    private boolean error;

    private boolean skip;

    private boolean defaultSetAdmin;

    private String errorMessage;

}
