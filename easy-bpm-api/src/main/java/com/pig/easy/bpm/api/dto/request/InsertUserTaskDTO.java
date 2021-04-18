package com.pig.easy.bpm.api.dto.request;

import com.pig.easy.bpm.common.generator.dto.request.BaseRequestDTO;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/20 10:21
 */
@Data
@ToString
public class InsertUserTaskDTO extends BaseRequestDTO {

    private static final long serialVersionUID = 3814070717733442774L;

    private Long taskId;

    private String actTaskId;

    private Long applyId;

    private Long processId;

    private String procInstId;

    private String tenantId;

    private String taskName;

    private String taskNodeCode;

    private Long parentTaskId;

    private Integer taskStatus;

    private Long roleGroupId;

    private Long roleId;

    private String roleName;

    private Long taskOwnerUserId;

    private String taskOwnerRealName;

    private Long taskAssigneeUserId;

    private String taskAssigneeRealName;

    private Integer taskPriority;

    private String formKey;

    private Date claimTime;

    private LocalDateTime dueTime;

    private LocalDateTime approveTime;

    private String system;

    private String platform;

    private String remarks;

    private Long operatorId;

    private String operatorName;

}
