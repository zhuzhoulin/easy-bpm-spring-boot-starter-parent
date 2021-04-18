package com.pig.easy.bpm.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author pig
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bpm_process_rule")
public class ProcessRuleDO extends Model<ProcessRuleDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 规则 guid
     */
    private String ruleId;

    /**
     * 规则名称
     */
    @TableField("`rule_name`")
    private String ruleName;

    /**
     * 规则编号
     */
    @TableField("`rule_code`")
    private String ruleCode;

    /**
     * 规则类型 1 人员规则
     */
    @TableField("`rule_type`")
    private Integer ruleType;

    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 流程编号
     */
    @TableField("`process_id`")
    private Integer processId;

    /**
     * 角色组编号
     */
    @TableField("`role_group_id`")
    private Integer roleGroupId;

    /**
     * 角色编号
     */
    @TableField("`role_id`")
    private Integer roleId;

    /**
     * 用户编号
     */
    @TableField("`user_id`")
    private Integer userId;

    /**
     * 规则表达式
     */
    @TableField("`rule_expression`")
    private String ruleExpression;

    /**
     * 规则动作
     */
    @TableField("`rule_action`")
    private String ruleAction;

    /**
     * 规则涉及人员（新增/减少）多个人以‘,’区分
     */
    @TableField("`rule_users`")
    private String ruleUsers;

    @TableField("`remarks`")
    private String remarks;

    @TableField("`valid_state`")
    private Integer validState;

    @TableField("`operator_id`")
    private Integer operatorId;

    @TableField("`operator_name`")
    private String operatorName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.ruleId;
    }

}
