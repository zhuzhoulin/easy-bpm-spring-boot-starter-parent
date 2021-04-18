package com.pig.easy.bpm.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 审批历史表
 * </p>
 *
 * @author pig
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("bpm_history")
public class HistoryDO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 审批记录表
     */
    @TableId(value = "history_id", type = IdType.AUTO)
    private Long historyId;

    /**
     * 申请编号
     */
    private Long applyId;

    /**
     * 任务编号
     */
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 来源系统
     */
    private String system;

    /**
     * 来源平台
     */
    private String platform;

    /**
     * 审批动作编码
     */
    private String approveActionCode;

    /**
     * 审批动作名称
     */
    private String approveActionName;

    /**
     * 审批意见
     */
    private String approveOpinion;

    /**
     * 审批人工号
     */
    private Long approveUserId;

    /**
     * 审批人姓名
     */
    private String approveRealName;

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
