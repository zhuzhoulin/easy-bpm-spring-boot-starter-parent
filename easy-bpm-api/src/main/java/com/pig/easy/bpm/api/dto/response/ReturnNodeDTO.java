package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/29 17:15
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ReturnNodeDTO extends BaseResponseDTO implements Serializable {

    private static final long serialVersionUID = 2542474039870637078L;

    private Long nodeUserId;

    private String nodeId;
    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点分配人员工号 多个人以 , 区分
     */
    private String assigneeUserIdList;

    private List<Long> assigneeUserIds;

    /**
     * 节点分配人员姓名 多个人以 , 区分
     */
    private String assigneeUserNameList;

    private List<String> assigneeUserNames;

}
