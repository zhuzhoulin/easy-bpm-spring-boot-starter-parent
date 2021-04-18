package com.pig.easy.bpm.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bpm_role")
public class RoleDO extends Model<RoleDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @TableId(value = "`role_id`", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色编码
     */
    @TableField("`role_code`")
    private String roleCode;

    /**
     * 角色名称
     */
    @TableField("`role_name`")
    private String roleName;

    /**
     * 角色级别
     */
    @TableField("`role_level`")
    private Integer roleLevel;

    /**
     * 角色简称
     */
    @TableField("`role_abbr`")
    private String roleAbbr;

    /**
     * 角色别名
     */
    @TableField("`role_alias_name`")
    private String roleAliasName;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 归属公司编号
     */
    @TableField("`company_id`")
    private Long companyId;

    /**
     * 归属部门编号
     */
    @TableField("`dept_id`")
    private Long deptId;

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
     * 更新时间
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
        return this.roleId;
    }

}
