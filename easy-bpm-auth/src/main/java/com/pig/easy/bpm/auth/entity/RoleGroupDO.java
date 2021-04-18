package com.pig.easy.bpm.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色组
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("bpm_role_group")
public class RoleGroupDO extends Model<RoleGroupDO> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`role_group_id`", type = IdType.AUTO)
    private Long roleGroupId;

    /**
     * 角色组编码
     */
    @TableField("`role_group_code`")
    private String roleGroupCode;

    /**
     * 角色组名称
     */
    @TableField("`role_group_name`")
    private String roleGroupName;

    /**
     * 角色组简称
     */
    @TableField("`role_group_abbr`")
    private String roleGroupAbbr;

    /**
     * 所属条线，如 level 2 会根据 条线 找 level 1
     */
    @TableField("`business_line`")
    private String businessLine;

    /**
     * 角色组等级
     */
    @TableField("`role_group_level`")
    private Integer roleGroupLevel;

    /**
     * 角色组类别
     */
    @TableField("`role_group_type`")
    private Integer roleGroupType;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

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
     * 操作人姓名
     */
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
        return this.roleGroupId;
    }

}
