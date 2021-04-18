package com.pig.easy.bpm.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("bpm_form_data")
public class FormDataDO extends Model<FormDataDO> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    @TableId(value = "`data_id`", type = IdType.INPUT)
    private Long dataId;

    /**
     * 数据KEY
     */
    @TableField("`data_key`")
    private String dataKey;

    /**
     * 数据中文名称
     */
    @TableField("`data_name`")
    private String dataName;

    /**
     * 字符串数据值
     */
    @TableField("`string_value`")
    private String stringValue;

    /**
     * boolean 值
     */
    @TableField("`boolean_value`")
    private Boolean booleanValue;

    /**
     * 数值值
     */
    @TableField("`number_value`")
    private BigDecimal numberValue;

    /**
     * 日期格式
     */
    @TableField("`number_format`")
    private String numberFormat;

    /**
     * 日期格式
     */
    @TableField("`date_value`")
    private LocalDateTime dateValue;

    /**
     * 日期格式
     */
    @TableField("`date_pattern`")
    private String datePattern;

    /**
     * select 选中值
     */
    @TableField("`select_value`")
    private String selectValue;

    /**
     * SELECT 下拉选项
     */
    @TableField("`select_item`")
    private String selectItem;

    /**
     * 大字段
     */
    @TableField("`text_value`")
    private String textValue;

    /**
     * 数据类型
     */
    @TableField("`data_type`")
    private String dataType;

    /**
     * 表单编号
     */
    @TableField("`form_id`")
    private Long formId;

    /**
     * 表单KEY
     */
    @TableField("`form_key`")
    private String formKey;

    /**
     * 租户
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 流程编号
     */
    @TableField("`process_id`")
    private Long processId;

    /**
     * 申请编号
     */
    @TableField("`apply_id`")
    private Long applyId;

    /**
     * 流程实例编号
     */
    @TableField("`proc_inst_id`")
    private String procInstId;

    /**
     * 任务编号
     */
    @TableField("`task_id`")
    private Long taskId;

    /**
     * 有效状态；0表示无效，1表示有效
     */
    @TableField("`valid_state`")
    private Integer validState;

    /**
     * create_time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 操作人工号
     */
    @TableField("`operator_id`")
    private Long operatorId;

    /**
     * operator_name
     */
    @TableField("`operator_name`")
    private String operatorName;


    @Override
    protected Serializable pkVal() {
        return this.dataId;
    }

}
