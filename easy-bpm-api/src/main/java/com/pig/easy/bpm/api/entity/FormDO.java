package com.pig.easy.bpm.api.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("bpm_form")
public class FormDO extends Model<FormDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 表单编号
     */
    @TableId(value = "`form_id`", type = IdType.AUTO)
    private Long formId;

    /**
     * 表单KEY
     */
    @TableField("`form_key`")
    private String formKey;

    /**
     * 表单名称
     */
    @TableField("`form_name`")
    private String formName;

    /**
     * 租户编号
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 表单类型 1 PC 表单 2 移动表单
     */
    @TableField("`form_type`")
    private Integer formType;

    /**
     * 排序
     */
    @TableField("`sort`")
    private Integer sort;

    /**
     * 表单json数据
     */
    @TableField("`form_data`")
    private String formData;

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
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

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


    @Override
    protected Serializable pkVal() {
        return this.formId;
    }

}
