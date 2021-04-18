package com.pig.easy.bpm.api.dto.response;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * todo:
 *
 * @author : pig
 * @date : 2020/6/26 17:23
 */
@Data
@ToString
public class ApplyDTO extends BaseResponseDTO {

    private static final long serialVersionUID = -8530181007435351194L;

    private Long applyId;
    /**
     * 单据编号
     */
    private String applySn;
    /**
     * 流程实例编号
     */
    private String procInstId;
    /**
     * 申请标题
     */
    private String applyTitle;
    /**
     * 申请人工号
     */
    private Long applyUserId;
    /**
     * 申请人姓名
     */
    private String applyRealName;
    /**
     * 部门编号
     */
    private Long applyDeptId;
    /**
     * 申请部门工号
     */
    private String applyDeptCode;
    /**
     * 申请部门名称
     */
    private String applyDeptName;
    /**
     * 申请人公司编号
     */
    private Long applyCompanyId;
    /**
     * 申请人公司编码
     */
    private String applyCompanyCode;
    /**
     * 申请人公司名称
     */
    private String applyCompanyName;
    /**
     * 租户编号
     */
    private String tenantId;
    /**
     * 流程编号
     */
    private Long processId;
    /**
     * 流程编码
     */
    private String processKey;
    /**
     * 流程名称
     */
    private String processName;
    /**
     * 发起流程版本ID
     */
    private String definitionId;

    private String formKey;
    /**
     * 来源系统
     */
    private String system;
    /**
     * 来源编码
     */
    private String platform;
    /**
     * 父级申请编码
     */
    private Long parentApplyId;
    /**
     * 流程状态 1 草稿 2 审批中 3 审批通过 4 审批拒绝  5 已取消
     */
    private Integer applyStatus;
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
     * 申请时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private LocalDateTime completeTime;

}
