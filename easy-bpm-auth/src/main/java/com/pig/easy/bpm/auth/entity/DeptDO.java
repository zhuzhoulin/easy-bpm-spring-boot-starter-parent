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
 * 部门表
 * </p>
 *
 * @author pig
 * @since 2021-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bpm_dept")
public class DeptDO extends Model<DeptDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    @TableId(value = "`dept_id`", type = IdType.AUTO)
    private Long deptId;

    /**
     * 部门编码
     */
    @TableField("`dept_code`")
    private String deptCode;

    /**
     * 部门名称
     */
    @TableField("`dept_name`")
    private String deptName;

    /**
     * 所属公司编码
     */
    @TableField("`company_id`")
    private Long companyId;

    /**
     * 所属公司编码
     */
    @TableField("`company_code`")
    private String companyCode;

    /**
     * 归属业务条线
     */
    @TableField("`business_line`")
    private String businessLine;

    /**
     * 所属租户
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 上级部门编号
     */
    @TableField("`dept_parent_id`")
    private Long deptParentId;

    /**
     * 上级部门编码
     */
    @TableField("`dept_parent_code`")
    private String deptParentCode;

    /**
     * 部门层级
     */
    @TableField("`dept_level`")
    private Integer deptLevel;

    /**
     * 部门类型
     */
    @TableField("`dept_type`")
    private String deptType;

    /**
     * 部门类型编码
     */
    @TableField("`dept_type_code`")
    private String deptTypeCode;

    /**
     * 备注
     */
    @TableField("`remark`")
    private String remark;

    /**
     * 排序
     */
    @TableField("`dept_order`")
    private Integer deptOrder;

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
        return this.deptId;
    }

}
