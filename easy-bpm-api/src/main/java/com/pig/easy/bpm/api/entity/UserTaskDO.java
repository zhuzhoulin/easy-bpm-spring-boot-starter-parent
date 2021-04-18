package com.pig.easy.bpm.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author pig
 * @since 2020-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("bpm_user_task")
public class UserTaskDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "task_id", type = IdType.AUTO)
    private Long taskId;
    /**
     * flowable 任务编号
     */
    @TableField("act_task_id")
    private String actTaskId;
    /**
     * 申请编号
     */
    @TableField("apply_id")
    private Long applyId;
    /**
     * 流程编号
     */
    @TableField("process_id")
    private Long processId;
    /**
     * 流程实例编号
     */
    @TableField("proc_inst_id")
    private String procInstId;
    /**
     * 所属租户
     */
    @TableField("tenant_id")
    private String tenantId;
    /**
     * 任务名称
     */
    @TableField("task_name")
    private String taskName;

    @TableField("task_type")
    private String taskType;

    /**
     * 任务对应节点编号
     */
    @TableField("task_node_code")
    private String taskNodeCode;
    /**
     * 父级任务编号
     */
    @TableField("parent_task_id")
    private Long parentTaskId;
    /**
     * 任务状态 1  表示未认领 2 表示已认领 3 表示已完成 4表示已取消 5：取消重新发起 6:找不到人员系统自动完成任务
     */
    @TableField("task_status")
    private Integer taskStatus;
    /**
     * 角色组编号
     */
    @TableField("role_group_code")
    private String roleGroupId;
    /**
     * 角色编号
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 任务归属人工号
     */
    @TableField("task_owner_user_id")
    private Long taskOwnerUserId;
    /**
     * 任务归属人姓名
     */
    @TableField("task_owner_real_name")
    private String taskOwnerRealName;
    /**
     * 任务处理人工号
     */
    @TableField("task_assignee_user_id")
    private Long taskAssigneeUserId;
    /**
     * 任务处理人姓名
     */
    @TableField("task_assignee_real_name")
    private String taskAssigneeRealName;
    /**
     * 任务优先等级
     */
    @TableField("task_priority")
    private Integer taskPriority;
    /**
     * 表单关联KEY
     */
    @TableField("form_key")
    private String formKey;
    /**
     * 任务认领时间
     */
    @TableField("claim_time")
    private Date claimTime;
    /**
     * 任务到期时间
     */
    @TableField("due_time")
    private LocalDateTime dueTime;
    /**
     * 任务审批时间
     */
    @TableField("approve_time")
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
    @TableField("valid_state")
    private Integer validState;
    /**
     * operator_id
     */
    @TableField("operator_id")
    private Long operatorId;
    /**
     * 操作人工姓名
     */
    @TableField("operator_name")
    private String operatorName;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
