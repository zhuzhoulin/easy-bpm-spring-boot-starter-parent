package com.pig.easy.bpm.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 申请表
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
@TableName("bpm_apply")
public class ApplyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`apply_id`", type = IdType.AUTO)
    private Long applyId;
    /**
     * 单据编号
     */
    @TableField("`apply_sn`")
    private String applySn;
    /**
     * 流程实例编号
     */
    @TableField("`proc_inst_id`")
    private String procInstId;
    /**
     * 申请标题
     */
    @TableField("`apply_title`")
    private String applyTitle;
    /**
     * 申请人工号
     */
    @TableField("`apply_user_id`")
    private Long applyUserId;
    /**
     * 申请人姓名
     */
    @TableField("`apply_real_name`")
    private String applyRealName;
    /**
     * 部门编号
     */
    @TableField("`apply_dept_id`")
    private Long applyDeptId;
    /**
     * 申请部门工号
     */
    @TableField("`apply_dept_code`")
    private String applyDeptCode;
    /**
     * 申请部门名称
     */
    @TableField("`apply_dept_name`")
    private String applyDeptName;
    /**
     * 申请人公司编号
     */
    @TableField("`apply_company_id`")
    private Long applyCompanyId;
    /**
     * 申请人公司编码
     */
    @TableField("`apply_company_code`")
    private String applyCompanyCode;
    /**
     * 申请人公司名称
     */
    @TableField("apply_company_name")
    private String applyCompanyName;
    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;
    /**
     * 流程编号
     */
    @TableField("`process_id`")
    private Long processId;
    /**
     * 流程编码
     */
    @TableField("`process_key`")
    private String processKey;
    /**
     * 流程名称
     */
    @TableField("`process_name`")
    private String processName;
    /**
     * 发起流程版本ID
     */
    @TableField("`definition_id`")
    private String definitionId;

    @TableField("`form_key`")
    private String formKey;

    /**
     * 来源系统
     */
    @TableField("`system`")
    private String system;
    /**
     * 来源编码
     */
    @TableField("`platform`")
    private String platform;
    /**
     * 父级申请编码
     */
    @TableField("`parent_apply_id`")
    private Long parentApplyId;
    /**
     * 流程状态 1 草稿 2 审批中 3 审批通过 4 审批拒绝  5 已取消
     */
    @TableField("`apply_status`")
    private Integer applyStatus;
    /**
     * 备注
     */
    @TableField("`remarks`")
    private String remarks;
    /**
     * 状态 1 有效 0 失效
     */
    @TableField("`valid_state`")
    private Integer validState;
    /**
     * 操作人工号
     */
    @TableField("`operator_id`")
    private Long operatorId;
    /**
     * 操作人工姓名
     */
    @TableField("operator_name")
    private String operatorName;
    /**
     * 申请时间
     */
    @TableField("`create_time`")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("`update_time`")
    private LocalDateTime updateTime;

    /**
     * 审批完成时间
     */
    @TableField("`complete_time`")
    private LocalDateTime completeTime;

}
