package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/26 15:15
 */
@Data
@ToString
public class UserTaskInfoDTO extends BaseResponseDTO {

    private static final long serialVersionUID = 8811013008684604545L;

    private Long applyId;

    private String applyTitle;

    private String applySn;

    private String procInstId;

    private Long applyUserId;

    private String applyRealName;

    private Long applyDeptId;

    private String applyDeptCode;

    private String applyDeptName;

    private Long applyCompanyId;

    private String applyCompanyCode;

    private String applyCompanyName;

    private String tenantId;

    private Long processId;

    private String processKey;

    private String processName;

    private String definitionId;

    private String system;
    /**
     * 来源编码
     */
    private String platform;

    private Long parentApplyId;

    private Integer applyStatus;

    private String remarks;

    private Integer validState;

    private Long operatorId;

    private String operatorName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long taskId;
    /**
     * flowable 任务编号
     */
    private String actTaskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务对应节点编号
     */
    private String taskNodeCode;
    /**
     * 父级任务编号
     */
    private Long parentTaskId;
    /**
     * 任务状态 1  表示未认领 2 表示已认领 3 表示已完成 4表示已取消 5：取消重新发起 6:找不到人员系统自动完成任务
     */
    private Integer taskStatus;
    /**
     * 角色组编号
     */
    private String roleGroupCode;
    /**
     * 角色编号
     */
    private String roleCode;

    private String taskType;


    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 任务归属人工号
     */
    private Long taskOwnerUserId;
    /**
     * 任务归属人姓名
     */
    private String taskOwnerRealName;
    /**
     * 任务处理人工号
     */
    private Long taskAssigneeUserId;
    /**
     * 任务处理人姓名
     */
    private String taskAssigneeRealName;
    /**
     * 任务优先等级
     */
    private Integer taskPriority;
    /**
     * 表单关联KEY
     */
    private String formKey;
    /**
     * 任务认领时间
     */
    private Date claimTime;
    /**
     * 任务到期时间
     */
    private LocalDateTime dueTime;
    /**
     * 任务审批时间
     */
    private LocalDateTime approveTime;

    private String currentApproveName;

    private LocalDateTime applyDate;

}
