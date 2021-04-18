package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/7/1 14:31
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class HistoryDTO extends BaseResponseDTO implements Serializable {

    private static final long serialVersionUID = -8416451917583505551L;

    /**
     * 审批记录表
     */
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
