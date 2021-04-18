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
 * @date : 2020/6/23 11:44
 */
@Data
@ToString
public class UserTaskSaveDTO extends BaseRequestDTO {

    private static final long serialVersionUID = -7080432945099615882L;

    private Long taskId;
    /**
     * flowable 任务编号
     */
    private String actTaskId;
    /**
     * 申请编号
     */

    private Long applyId;
    /**
     * 流程编号
     */

    private Long processId;
    /**
     * 流程实例编号
     */

    private String procInstId;
    /**
     * 所属租户
     */

    private String tenantId;
    /**
     * 任务名称
     */

    private String taskName;

    private String taskType;
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
    /**
     * 来源系统
     */
    private String system;
    /**
     * 来源平台
     */
    private String platform;
    /**
     * 任务备注
     */
    private String remarks;
    /**
     * 状态 1 有效 0 失效
     */

    private Integer validState;
    /**
     * operator_id
     */

    private Long operatorId;
    /**
     * 操作人工姓名
     */

    private String operatorName;
    /**
     * 创建时间
     */

    private LocalDateTime createTime;
    /**
     * 更新时间
     */

    private LocalDateTime updateTime;

}
