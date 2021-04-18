package com.pig.easy.bpm.auth.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
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
@TableName("bpm_user")
public class UserDO extends Model<UserDO> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户编号
     */
    @TableField("`user_id`")
    private Long userId;

    /**
     * 用户账户
     */
    @TableField("`user_name`")
    private String userName;

    /**
     * 用户名称
     */
    @TableField("`real_name`")
    private String realName;

    /**
     * 邮箱
     */
    @TableField("`email`")
    private String email;

    /**
     * 联系方式
     */
    @TableField("`phone`")
    private String phone;

    /**
     * 岗位编码
     */
    @TableField("`position_code`")
    private String positionCode;

    /**
     * 岗位名称
     */
    @TableField("`position_name`")
    private String positionName;

    /**
     * 性别，0表示未知，1表示男，2表示女
     */
    @TableField("`gender`")
    private Integer gender;

    /**
     * 头像图片链接
     */
    @TableField("`avatar`")
    private String avatar;

    /**
     * 生日
     */
    @TableField("`birth_date`")
    private LocalDateTime birthDate;

    /**
     * 公司编号
     */
    @TableField("`company_id`")
    private Long companyId;

    /**
     * 部门编号
     */
    @TableField("`dept_id`")
    private Long deptId;

    /**
     * 当前用户活动租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 密码
     */
    @TableField("`password`")
    private String password;

    /**
     * 入职时间
     */
    @TableField("`entry_time`")
    private LocalDateTime entryTime;

    /**
     * 离职时间
     */
    @TableField("`leave_time`")
    private LocalDateTime leaveTime;

    /**
     * 雇佣状态 1 在职 2 离职
     */
    @TableField("`hire_status`")
    private Integer hireStatus;

    /**
     * 来源： system 系统，people：人工 ldap：ldap
     */
    @TableField("`source`")
    private String source;

    /**
     * 是否锁定 1 锁定 0 未锁定
     */
    @TableField("`lock_status`")
    private Integer lockStatus;

    /**
     * 有效状态；0表示无效，1表示有效
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
        return this.id;
    }

}
